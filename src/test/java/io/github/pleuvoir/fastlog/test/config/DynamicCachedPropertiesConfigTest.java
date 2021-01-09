package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.config.DynamicCachedPropertiesConfig;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class DynamicCachedPropertiesConfigTest {


    //测试时必须通过文件系统修改，不要在IDEA中修改文件否则识别不到刷新
    @Test
    public void test() throws InterruptedException {

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            DynamicCachedPropertiesConfig.getString("age");
            DynamicCachedPropertiesConfig.printAll();
        }
    }
}
