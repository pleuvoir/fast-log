package io.github.pleuvoir.fastlog.test.config;

import io.github.pleuvoir.fastlog.config.PropertiesConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class PropertiesConfigTest {

    @Test
    public void test(){
        String value = PropertiesConfig.getString("name   ");
        Assert.assertEquals("pleuvoir",value);
    }
}
