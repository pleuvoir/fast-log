package io.github.pleuvoir.fastlog.constants;

import io.github.pleuvoir.fastlog.config.LogConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class Const {

    //调试
    public static final int DEBUG = 0;
    //普通
    public static final int INFO = 1;
    //告警
    public static final int WARN = 2;
    //错误
    public static final int ERROR = 3;
    //严重
    public static final int FATAl = 4;

    //配置文件定义的日志级别
    public static final String CFG_LOG_LEVEL = LogConfig.INSTANCE.getString("CFG_LOG_LEVEL", "INFO");
    //配置文件定义的日志输出路径
    public static final String CFG_LOG_PATH = LogConfig.INSTANCE.getString("CFG_LOG_PATH", "opt/fastlog/logs");
    //是否输出到控制台
    public static final boolean CONSOLE_PRINT_ENABLED = LogConfig.INSTANCE.getBoolean("CONSOLE_PRINT_ENABLED", false);
    //当前环境的字符集
    public static final String CFG_CHARSET_NAME = LogConfig.INSTANCE.getString("CFG_CHARSET_NAME", "UTF-8");
    //日志文件名称
    public static final String LOG_FILE_NAME = "fastlog.properties";


    public static final Map<String, String> LEVEL_DICT = new HashMap<String, String>() {{
        put("0", "DEBUG");
        put("1", "INFO");
        put("2", "WARN");
        put("3", "ERROR");
        put("4", "FATAl");
    }};
}
