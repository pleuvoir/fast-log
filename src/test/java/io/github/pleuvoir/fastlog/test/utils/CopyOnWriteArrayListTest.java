package io.github.pleuvoir.fastlog.test.utils;

import io.github.pleuvoir.fastlog.log.FastLogItem;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class CopyOnWriteArrayListTest {

    private static FastLogItem active = new FastLogItem();

    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(1);

        active.getBufferA().add(new StringBuffer("first"));

        final List<StringBuffer> bufferA = active.getBufferA();

        new Thread(new Runnable() {
            @Override
            public void run() {
                active.getBufferA().add(new StringBuffer("second"));
                latch.countDown();
            }
        }).start();

        latch.await();

        System.out.println(bufferA); //[first, second]
    }
}
