package io.github.pleuvoir.fastlog.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 双缓冲队列
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@SuppressWarnings("all")
public class FastLogItem {


    private static final char DEFAULT_ACTIVE = 'A';

    public char curBuffer = DEFAULT_ACTIVE;

    private List<StringBuffer> bufferA = new ArrayList<>();
    private List<StringBuffer> bufferB = new ArrayList<>();

    public List<StringBuffer> getBufferA() {
        return bufferA;
    }

    public void setBufferA(List<StringBuffer> bufferA) {
        this.bufferA = bufferA;
    }

    public List<StringBuffer> getBufferB() {
        return bufferB;
    }

    public void setBufferB(List<StringBuffer> bufferB) {
        this.bufferB = bufferB;
    }
}
