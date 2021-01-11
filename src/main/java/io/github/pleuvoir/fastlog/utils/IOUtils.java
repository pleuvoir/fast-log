package io.github.pleuvoir.fastlog.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IO处理工作
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class IOUtils {


    public static boolean mkdirs(String dirPath) {
        final File dir = new File(dirPath);
        if (!dir.exists() && !dir.isDirectory()) {
            return dir.mkdirs();
        }
        return true;
    }


    public static boolean createNewFile(String filePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
                return false;
            }
        }
        return true;
    }


    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException ignored) {
            }
        }
    }
}
