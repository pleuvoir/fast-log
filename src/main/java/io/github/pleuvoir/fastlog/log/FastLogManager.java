package io.github.pleuvoir.fastlog.log;

import io.github.pleuvoir.fastlog.config.LogConfig;
import io.github.pleuvoir.fastlog.constants.Const;
import io.github.pleuvoir.fastlog.utils.DateFormat;
import io.github.pleuvoir.fastlog.utils.IOUtils;
import io.github.pleuvoir.fastlog.utils.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志管理类<br>
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FastLogManager extends Thread {

    public static final Map<String /** filename**/, FastLogItem> ITEMS = new ConcurrentHashMap<>();

    //刷盘间隔 默认1秒
    public static final long FLUSH_INTERVAL = LogConfig.getInstance().getLong("FLUSH_INTERVAL", 1000L);

    //刷盘缓存大小 默认1M
    public static final long FLUSH_CACHE_SIZE = LogConfig.getInstance().getLong("FLUSH_INTERVAL", 1024 * 1024L);

    //单个文件的最大大小 默认10M
    public static final long SINGLE_FILE_MAX_SIZE = LogConfig.getInstance().getLong("SINGLE_FILE_MAX_SIZE", 10 * 1024 * 1024L);

    private boolean running = true;

    private static volatile FastLogManager logManager;
    private static final Object MUTEX = new Object();

    private FastLogManager() {
        this.setName("FastLogManager");
        this.start();
    }

    public static FastLogManager getInstance() {
        if (logManager == null) {
            synchronized (MUTEX) {
                if (logManager == null) {
                    logManager = new FastLogManager();
                }
            }
        }
        return logManager;
    }


    /**
     * 创建日志文件
     */
    private static void createLogFile(FastLogItem logItem) {
        //创建日志ROOT目录
        if (!IOUtils.mkdirs(Const.CFG_LOG_PATH)) {
            return;
        }

        //第一次创建文件
        final String currentDate = DateFormat.DATE_COMPACT.format(new Date());
        if (StringUtils.isBlank(logItem.getFullLogFileName()) || !logItem.getLastWritedate().equals(currentDate)) {
            supplyFile(logItem, currentDate);
        }

        //标记文件大小
        final File file = new File(logItem.getFullLogFileName());
        logItem.setCurLogFileSize(file.length());

        /*
         *  如果超过单个文件最大大小则备份文件，并创建新文件。
         *  这个文件的大小可能会超出设置的最大文件大小，比如第一次创建的文件就已经超出了最大值，第二次flush时就会备份，此时就超出了大小。
         */
        if (!StringUtils.isBlank(logItem.getFullLogFileName()) && logItem.getCurLogFileSize() > SINGLE_FILE_MAX_SIZE) {
            final File oldFile = new File(logItem.getFullLogFileName());
            if (oldFile.exists()) {
                String newFileName =
                        Const.CFG_LOG_PATH + "/" + logItem.getLogFileName() + "_" + DateFormat.MS.format(new Date())
                                + ".log";
                final File newFile = new File(newFileName);
                final boolean b = oldFile.renameTo(newFile);
                System.out.println("日志已备份为" + newFileName + (b ? "成功" : "失败"));
                //重新创建文件
                supplyFile(logItem, currentDate);
            }
        }
    }


    private static void supplyFile(FastLogItem logItem, String currentDate) {
        //子目录
        String subDir = Const.CFG_LOG_PATH + "/" + currentDate;
        IOUtils.mkdirs(subDir);
        //完整的日志文件路径
        final String fullLogFileName = subDir + "/" + logItem.getLogFileName() + ".log";
        logItem.setFullLogFileName(fullLogFileName);
        //最后一次写入的日期
        logItem.setLastWritedate(currentDate);

        final File file = new File(fullLogFileName);
        if (!file.exists()) {
            IOUtils.createNewFile(logItem.getFullLogFileName());
            logItem.setCurLogFileSize(0);
        } else {
            //当应用重启后会需要重新设置该文件大小
            logItem.setCurLogFileSize(file.length());
        }
    }


    private static void flush(boolean forceFlush) {
        final long currentTimeMillis = System.currentTimeMillis();

        for (FastLogItem item : ITEMS.values()) {
            List<StringBuffer> currentBuffer;
            if (currentTimeMillis >= item.getNextWriteTimeStamp()
                    || item.getCurCacheSize() > FLUSH_CACHE_SIZE
                    || forceFlush) {

                //切换buffer，此时新的写入会切换到另一个buffer中，可避免使用一个buffer写入磁盘时缓冲依然再追加以及可能清空新追加内容的问题
                final char curBuffer = item.curBuffer;
                if (curBuffer == 'A') {
                    currentBuffer = item.getBufferA();
                    item.curBuffer = 'B';
                } else {
                    currentBuffer = item.getBufferB();
                    item.curBuffer = 'A';
                }

                //创建或者备份日志文件
                createLogFile(item);
                //写入文件内容
                writeToFile(item.getFullLogFileName(), currentBuffer);
            }
        }
    }


    ///添加日志信息到数据结构中，以供异步flush
    public void addLog(String filename, String message) {
        FastLogItem active = ITEMS.get(filename);
        if (active == null) {
            FastLogItem item = new FastLogItem();
            item.setNextWriteTimeStamp(System.currentTimeMillis() + FLUSH_INTERVAL);
            item.setLogFileName(filename);
            //putIfAbsent 如果不存在就 put 并且不返回值，否则就 get，正常情况第一次放返回为 null
            ITEMS.putIfAbsent(filename, item);
            //不管之前有没有，反正现在一定是可以拿到的
            active = ITEMS.get(filename);
        }
        active.setNextWriteTimeStamp(System.currentTimeMillis() + FLUSH_INTERVAL);

        //保存日志信息到队列
        //这里注意使用线程安全的list，否则add时普通的arrayList会报NPE
        if (active.curBuffer == 'A') {
            active.getBufferA().add(new StringBuffer(message));
        } else {
            active.getBufferB().add(new StringBuffer(message));
        }
    }

    /**
     * 写文件
     *
     * @param filePath 文件全路径
     * @param buffers  缓冲
     * @return 字节大小
     */
    public static int writeToFile(String filePath, List<StringBuffer> buffers) {
        final boolean b = IOUtils.createNewFile(filePath);
        if (!b) {
            return 0;
        }

        int size = 0;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, true); //顺序追加写
            for (final StringBuffer buffer : buffers) {
                final byte[] bytes = buffer.toString().getBytes(Charset.defaultCharset());
                fos.write(bytes);
                fos.flush();
                size += bytes.length;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            IOUtils.close(fos);
            buffers.clear();
        }
        return size;
    }

    @Override
    public void run() {
        int i = 0;
        while (running) {
            flush(false);
            i++;
            //每100次强制刷盘
            if (i % 100 == 0) {
                flush(true);
                //手动赋值触发
                Const.CFG_LOG_LEVEL = LogConfig.getInstance().getString("CFG_LOG_LEVEL", Const.INFO);
                Const.CONSOLE_PRINT_ENABLED = LogConfig.getInstance().getBoolean("CONSOLE_PRINT_ENABLED", false);
            }
        }
    }

    /**
     * 优雅关闭，强制刷新
     */
    public void close() {
        running = false;
        flush(true);
    }
}
