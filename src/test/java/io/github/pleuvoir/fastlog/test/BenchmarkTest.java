package io.github.pleuvoir.fastlog.test;

import io.github.pleuvoir.fastlog.FastLog;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class BenchmarkTest {

    private static final int threadNum = 2;
    private FastLog log = FastLog.getInstance();

    private int loop = 20;
    private AtomicInteger count = new AtomicInteger(0);

    @Test
    public void test() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(loop);
        final ExecutorService pool = Executors.newFixedThreadPool(threadNum);

        final long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            pool.execute(() -> {
                log.info("hello world.hello world.");
                count.incrementAndGet();
                latch.countDown();
            });
        }
        latch.await();

        final long cost = System.currentTimeMillis() - start;

        System.out.println(String.format("cost %s ms,count=%s,throughput=%s", cost, count.get(), count.get() / cost * 1000));

    }
}
