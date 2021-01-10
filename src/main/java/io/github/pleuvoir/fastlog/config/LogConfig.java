package io.github.pleuvoir.fastlog.config;

/**
 * 日志文件配置
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class LogConfig extends ReloadPropertiesable {

    //日志文件名称
    private static String LOG_FILE_NAME = "fastlog.properties";

    private LogConfig(String propPath) {
        super(propPath);
    }

    public static LogConfig getInstance() {
        return LoaderHelper.INSTANCE;
    }

    public static class LoaderHelper {

        static LogConfig INSTANCE = new LogConfig(LOG_FILE_NAME);
    }
}
