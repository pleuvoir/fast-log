package io.github.pleuvoir.fastlog.test.utils;

import io.github.pleuvoir.fastlog.utils.IOUtils;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class IOUtilsTest {

    @Test
    public void test(){
        final boolean b = IOUtils.mkdirs("/Users/pleuvoir/dev/space/git/fast-log/dirs");
        System.out.println(b);
    }

}
