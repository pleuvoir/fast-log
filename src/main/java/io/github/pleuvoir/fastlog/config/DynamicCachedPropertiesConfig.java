package io.github.pleuvoir.fastlog.config;

import io.github.pleuvoir.fastlog.constants.Const;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 动态配置文件加载
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@SuppressWarnings("all")
public final class DynamicCachedPropertiesConfig {

    private static Map<String /** 文件绝对路径**/, PropertiesWrap> cache = new HashMap<>();


    public static String getString(String key) {
        return getString(Const.LOG_FILE_NAME, key);
    }


    private static String getString(String filePath, String key) {
        File file = toFile(filePath);
        if (file == null) {
            return null;
        }

        String absolutePath = file.getAbsolutePath();

        PropertiesWrap prev = cache.get(absolutePath);
        if (prev == null) {
            loadToCache(file);
            PropertiesWrap cur = cache.get(absolutePath);
            return cur.getProp().getProperty(key.trim()).trim();
        }

        //如果文件发生更改则重新加载
        long prevLastModified = prev.getLastModified();

        if (prevLastModified < file.lastModified()) {
            loadToCache(file);
            PropertiesWrap cur = cache.get(absolutePath);
            return cur.getProp().getProperty(key.trim()).trim();
        }

        return prev.getProp().getProperty(key.trim()).trim();
    }

    /**
     * 先尝试从绝对路径获取，如果获取失败则从classpath获取，若未找到则返回null
     */
    private static File toFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            URL url = Thread.currentThread().getContextClassLoader().getResource(filePath);
            if (url == null) {
                return null;
            }
            URI uri;
            try {
                uri = url.toURI();
                return new File(uri.getPath());
            } catch (URISyntaxException e) {
                return null;
            }
        }
        return file;
    }

    public static void printAll() {
        for (Entry<String, PropertiesWrap> entry : cache.entrySet()) {
            System.out.println(entry.getKey() + "|" + entry.getValue());
        }
    }

    private static void loadToCache(File file) {
        Properties prop = new Properties();
        try (
                Reader reader = new BufferedReader(new FileReader(file));
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

        @Override
        public String toString() {
            return "PropertiesWrap{" +
                    "lastModified=" + lastModified +
                    ", prop=" + prop +
                    '}';
        }
    }

}
