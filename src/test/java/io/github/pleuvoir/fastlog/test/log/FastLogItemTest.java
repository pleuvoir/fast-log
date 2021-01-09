package io.github.pleuvoir.fastlog.test.log;

import io.github.pleuvoir.fastlog.log.FastLogItem;
import java.util.ArrayList;
import java.util.List;
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

}
