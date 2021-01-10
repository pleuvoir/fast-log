package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.FastLog;
import io.github.pleuvoir.fastlog.config.LogConfig;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class LogConfigTest {

    private FastLog log = FastLog.getInstance();

    @Test
    public void test() throws InterruptedException {


        while (true) {
            final String name = LogConfig.getInstance().getString("name");
            System.out.println(name);
            final byte[] bytes = LogConfig.getInstance().getBytes("name", Charset.defaultCharset());

            System.out.println(new String(bytes));
            TimeUnit.SECONDS.sleep(3);
        }

    }
}
