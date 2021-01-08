package io.github.pleuvoir.fastlog.config;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * 配置文件读取
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public final class PropertiesConfig {

    private static Properties pro = new Properties();


    static {
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }


    /**
     * 读取classpath配置文件
     */
    private static void load() throws Exception {
        //toURI可解决路径中文乱码问题
        //Java应用的线程的上下文类加载器默认就是系统类加载器（ClassLoader.getSystemClassLoader() 默认就是AppClassloader）
        //类加载器的问题可查看：https://www.cnblogs.com/doit8791/p/5820037.html
        try (
                BufferedReader reader = Files.newBufferedReader(
                        Paths.get(Thread.currentThread().getContextClassLoader().getResource("fastlog.properties").toURI()))
        ) {
            pro.load(reader);
        }
    }


    /**
     * 获取文本字符串，剔除空格
     */
    public static String getString(String key) {
        return pro.getProperty(key.trim()).trim();
    }

}
