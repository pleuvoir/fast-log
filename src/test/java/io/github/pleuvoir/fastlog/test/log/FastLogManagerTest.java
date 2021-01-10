package io.github.pleuvoir.fastlog.test.log;

import io.github.pleuvoir.fastlog.log.FastLogManager;
import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FastLogManagerTest {




    @Test
    public void testConst(){

        System.out.println(FastLogManager.FLUSH_CACHE_SIZE);
        System.out.println(FastLogManager.FLUSH_INTERVAL);
        System.out.println(FastLogManager.SINGLE_FILE_MAX_SIZE);
    }

    @Test
    public void testInstance() throws InterruptedException {

        final ExecutorService pool = Executors.newFixedThreadPool(10);

        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                FastLogManager instance = FastLogManager.getInstance();
                System.out.println(Thread.currentThread().getName() + "=" + instance);
                latch.countDown();
            });
        }

        latch.await();
    }
}
