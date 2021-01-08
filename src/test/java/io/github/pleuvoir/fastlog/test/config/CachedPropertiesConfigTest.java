package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.config.CachedPropertiesConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class CachedPropertiesConfigTest {

    @Test
    public void test() {
        String value = CachedPropertiesConfig.getString("/Users/pleuvoir/dev/space/git/fast-log/src/test/resources/fastlog.properties",
                "name   ");
        Assert.assertEquals("pleuvoir", value);

        String value2 = CachedPropertiesConfig.getString("/Users/pleuvoir/dev/space/git/fast-log/src/test/resources/fastlog.properties",
                "name   ");
        Assert.assertEquals("pleuvoir", value2);
    }
}
