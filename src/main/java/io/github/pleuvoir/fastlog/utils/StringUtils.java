package io.github.pleuvoir.fastlog.utils;

/**
 * 字符串工具类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class StringUtils {

    public static boolean isBlank(String value) {
        return value == null || value.length() == 0;
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

}
