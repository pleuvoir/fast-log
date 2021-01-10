package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.config.LogConfig;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class LogConfigTest {

    @Test
    public void test() throws InterruptedException {

        while (true) {
            TimeUnit.SECONDS.sleep(3);
            final String name = LogConfig.INSTANCE.getString("name");
            System.out.println(name);
            final byte[] bytes = LogConfig.INSTANCE.getBytes("name", Charset.defaultCharset());

            System.out.println(new String(bytes));
        }

    }
}
