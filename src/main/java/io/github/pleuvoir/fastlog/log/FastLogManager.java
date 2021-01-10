package io.github.pleuvoir.fastlog.log;

import io.github.pleuvoir.fastlog.config.LogConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志管理类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FastLogManager {

    public static final Map<String, FastLogItem> ITEMS = new ConcurrentHashMap<>();

    //刷盘间隔 默认1秒
    public static final long FLUSH_INTERVAL = LogConfig.INSTANCE.getLong("FLUSH_INTERVAL", 1000L);

    //刷盘缓存大小 默认1M
    public static final long FLUSH_CACHE_SIZE = LogConfig.INSTANCE.getLong("FLUSH_INTERVAL", 1024 * 1024L);

    //单个文件的最大大小 默认10M
    public static final long SINGLE_FILE_MAX_SIZE = LogConfig.INSTANCE.getLong("SINGLE_FILE_MAX_SIZE", 10 * 1024 * 1024L);


    private static volatile FastLogManager logManager;
    private static final Object MUTEX = new Object();

    private FastLogManager() {

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

}
