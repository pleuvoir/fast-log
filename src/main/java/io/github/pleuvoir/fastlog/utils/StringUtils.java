package io.github.pleuvoir.fastlog.utils;

import com.sun.org.apache.regexp.internal.RE;

/**
 * 字符串工具类
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class StringUtils {

    public static boolean isBlank(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }

}
