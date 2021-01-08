package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.config.DynamicCachedPropertiesConfig;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class DynamicCachedPropertiesConfigTest {

    @Test
    public void test() throws InterruptedException {
        String value = DynamicCachedPropertiesConfig.getString("/Users/pleuvoir/dev/space/git/fast-log/src/test/resources/fastlog.properties",
                "name   ");
        Assert.assertEquals("pleuvoir", value);

        TimeUnit.SECONDS.sleep(10);

        String value2 = DynamicCachedPropertiesConfig.getString("/Users/pleuvoir/dev/space/git/fast-log/src/test/resources/fastlog.properties",
                "name   ");
        Assert.assertEquals("pleuvoir", value2);
    }
}
