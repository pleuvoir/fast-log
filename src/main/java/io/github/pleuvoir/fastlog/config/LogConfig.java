package io.github.pleuvoir.fastlog.config;

import io.github.pleuvoir.fastlog.constants.Const;

/**
 * 日志文件配置
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class LogConfig extends ReloadPropertiesable {

    //保证单例
    public static final LogConfig INSTANCE = new LogConfig(Const.LOG_FILE_NAME);

    private LogConfig(String propPath) {
        super(propPath);
    }

}
