package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.config.LogConfig;
import io.github.pleuvoir.fastlog.constants.Const;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ConstTest {

    @Test
    public void test() throws InterruptedException {

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Const.CFG_LOG_PATH);
            System.out.println(Const.CFG_LOG_LEVEL);
            System.out.println(Const.CFG_CHARSET_NAME);
            System.out.println(Const.CONSOLE_PRINT_ENABLED);
        }

    }
}
