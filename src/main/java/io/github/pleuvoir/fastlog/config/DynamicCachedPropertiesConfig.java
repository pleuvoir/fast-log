package io.github.pleuvoir.fastlog.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 动态配置文件加载
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public final class DynamicCachedPropertiesConfig {

    private static final Map<String /** 文件绝对路径**/, PropertiesWrap> cache = new HashMap<>();


    public static String getString(String filePath, String key) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        PropertiesWrap prev = cache.get(filePath);
        if (prev == null) {
            loadToCache(file);
            PropertiesWrap cur = cache.get(filePath);
            return cur.getProp().getProperty(key.trim()).trim();
        }

        //如果文件发生更改则重新加载
        long prevLastModified = prev.getLastModified();
        if (prevLastModified < file.lastModified()) {
            loadToCache(file);
            PropertiesWrap cur = cache.get(filePath);
            return cur.getProp().getProperty(key.trim()).trim();
        }

        return prev.getProp().getProperty(key.trim()).trim();
    }


    private static void loadToCache(File file) {
        Properties prop = new Properties();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(file.toURI()))
        ) {
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return;
        }
        long lastModified = file.lastModified();
        cache.put(file.getAbsolutePath(), new PropertiesWrap(lastModified, prop));
    }

    public static final class PropertiesWrap {

        private long lastModified;
        private Properties prop;

        PropertiesWrap(long lastModified, Properties prop) {
            this.lastModified = lastModified;
            this.prop = prop;
        }

        long getLastModified() {
            return lastModified;
        }

        Properties getProp() {
            return prop;
        }
    }

}
