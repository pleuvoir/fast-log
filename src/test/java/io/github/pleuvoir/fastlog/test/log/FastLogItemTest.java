package io.github.pleuvoir.fastlog.test.log;

import io.github.pleuvoir.fastlog.log.FastLogItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class FastLogItemTest {

    List<StringBuffer> current;

    @Test
    public void test() {
        final FastLogItem logItem = new FastLogItem();

        final ArrayList<StringBuffer> data = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("test,")
                .append("test,test");
        data.add(sb);

        sb = new StringBuffer();
        sb.append("test2,")
                .append("test2,test2");

        data.add(sb);

        logItem.setBufferA(data);

        //当前使用的队列
        current = logItem.getBufferA();

        //默认是A
        if (logItem.curBuffer == 'A') {
            //切换为B
            logItem.curBuffer = 'B';

            for (StringBuffer buffer : current) {
                System.out.println(buffer);
            }
            //清空A
            logItem.setBufferA(null);
            current = logItem.getBufferB();

        } else {
            logItem.curBuffer = 'A';
            for (StringBuffer buffer : current) {
                System.out.println(buffer);
            }
            logItem.setBufferB(null);
            current = logItem.getBufferA();
        }

    }

    //刷盘间隔
    public long flushInterval = 1000L;
    //刷盘缓存大小 10M
    public long flushCacheSize = 10 * 1024 * 1024;

    @Test
    public void testFlush() throws InterruptedException {
        //如果时间超了 或者大小超过 则刷盘

        final FastLogItem logItem = new FastLogItem();

        logItem.setCurCacheSize(logItem.getCurCacheSize() + 1 * 1024 * 1024);
        logItem.setNextWriteTimeStamp(System.currentTimeMillis() + flushInterval);

        TimeUnit.SECONDS.sleep(2);
        if (System.currentTimeMillis() >= logItem.getNextWriteTimeStamp() || logItem.getCurCacheSize() >= flushCacheSize) {
            System.out.println("刷盘");
        }

    }


}
