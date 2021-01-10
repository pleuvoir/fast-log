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
    public static final String DEBUG = "0";
    //普通
    public static final String INFO = "1";
    //告警
    public static final String WARN = "2";
    //错误
    public static final String ERROR = "3";
    //严重
    public static final String FATAl = "4";

    //配置文件定义的日志级别
    public static String CFG_LOG_LEVEL = LogConfig.getInstance().getString("CFG_LOG_LEVEL", INFO);
    //配置文件定义的日志输出路径
    public static String CFG_LOG_PATH = LogConfig.getInstance().getString("CFG_LOG_PATH", "/opt/fastlog/logs");
    //是否输出到控制台
    public static boolean CONSOLE_PRINT_ENABLED = LogConfig.getInstance().getBoolean("CONSOLE_PRINT_ENABLED", false);
    //当前环境的字符集
    public static String CFG_CHARSET_NAME = LogConfig.getInstance().getString("CFG_CHARSET_NAME", "UTF-8");


    public static final Map<String, String> LEVEL_DICT = new HashMap<String, String>() {{
        put("0", "DEBUG");
        put("1", "INFO");
        put("2", "WARN");
        put("3", "ERROR");
        put("4", "FATAl");
    }};
}
