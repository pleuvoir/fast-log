package io.github.pleuvoir.fastlog.test.utils;

import io.github.pleuvoir.fastlog.utils.ExceptionUtils;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ExceptionUtilsTest {

    @Test
    public void test(){

        System.out.println(ExceptionUtils.getStackTrace(new Exception("hello world")));
    }
}
