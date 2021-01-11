package io.github.pleuvoir.fastlog;

import io.github.pleuvoir.fastlog.constants.Const;
import io.github.pleuvoir.fastlog.log.FastLogManager;
import io.github.pleuvoir.fastlog.utils.DateFormat;
import io.github.pleuvoir.fastlog.utils.ExceptionUtils;
import io.github.pleuvoir.fastlog.utils.StringUtils;
import java.util.Date;

/**
 * 日志
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FastLog {

    private static volatile FastLog instance;
    private static final Object MUTEX = new Object();

    private static FastLogManager logManager = FastLogManager.getInstance();

    private FastLog() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logManager.close();
        }));
    }

    public static FastLog getInstance() {
        if (instance == null) {
            synchronized (MUTEX) {
                if (instance == null) {
                    instance = new FastLog();
                }
            }
        }
        return instance;
    }

    public void debug(String message) {
        writeLog("debug", Const.DEBUG, message);
    }

    public void fatal(String message) {
        writeLog("fatal", Const.FATAl, message);
    }

    public void warn(String message) {
        writeLog("warn", Const.WARN, message);
    }

    public synchronized void info(String message) {
        writeLog("info", Const.INFO, message);
    }

    public void error(String message) {
        writeLog("error", Const.ERROR, message);
    }

    private void writeLog(String logFileName, String level, String message) {
        //包含日志级别才打印
        if (StringUtils.isNotBlank(message) && Const.CFG_LOG_LEVEL.contains(level)) {
            String sb = "["
                    + Const.LEVEL_DICT.get(level)
                    + "]"
                    + DateFormat.DATETIME_DEFAULT.format(new Date())
                    + "["
                    + Thread.currentThread().getName()
                    + "] "
                    + message
                    + "\n";
            logManager.addLog(logFileName, sb);

            //错误信息强制打印控制台
            if (Const.ERROR.equals(level)
                    || Const.FATAl.equals(level)
                    || Const.CONSOLE_PRINT_ENABLED) {

                try {
                    System.out.print(sb);
                } catch (Throwable e) {
                    System.err.println(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }
}
