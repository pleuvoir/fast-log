package io.github.pleuvoir.fastlog.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 带有缓存的配置文件读取
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public final class CachedPropertiesConfig {

    private static final Map<String /** 文件绝对路径**/, Properties> cache = new HashMap<>();


    public static String getString(String filePath, String key) {
        Properties prev = cache.get(filePath);
        if (prev == null) {
            loadToCache(filePath);
        }

        Properties cur = cache.get(filePath);
        return cur == null ? "" : cur.getProperty(key.trim()).trim();
    }

    private static void loadToCache(String filePath) {
        Properties pro = new Properties();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))
        ) {
            pro.load(reader);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return;
        }
        cache.put(filePath, pro);
    }

}
