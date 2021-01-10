package io.github.pleuvoir.fastlog.test;

import io.github.pleuvoir.fastlog.FastLog;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FastLogTest {

    private FastLog log = FastLog.getInstance();

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            log.info("你好");
            TimeUnit.SECONDS.sleep(1);
        }

    }
}
