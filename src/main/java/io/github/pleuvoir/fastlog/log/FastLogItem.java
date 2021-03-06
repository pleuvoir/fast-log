package io.github.pleuvoir.fastlog.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 日志结构 <br> 双缓冲队列
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@SuppressWarnings("all")
public class FastLogItem {


    //不包含后缀的文件名
    public String logFileName = "";
    //完整路径名称
    public String fullLogFileName = "";
    //当前日志文件大小
    public long curLogFileSize = 0;
    //当前缓冲大小
    public long curCacheSize = 0;
    //下次日志输出到文件时间
    public long nextWriteTimeStamp = 0;
    //上次写入的日期
    private String lastWritedate;


    private static final char DEFAULT_ACTIVE = 'A';

    public char curBuffer = DEFAULT_ACTIVE;

    //这里注意使用线程安全的list，否则add时普通的arrayList会报NPE
    private List<StringBuffer> bufferA = new CopyOnWriteArrayList<>();
    private List<StringBuffer> bufferB = new CopyOnWriteArrayList<>();

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

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getFullLogFileName() {
        return fullLogFileName;
    }

    public void setFullLogFileName(String fullLogFileName) {
        this.fullLogFileName = fullLogFileName;
    }

    public long getCurLogFileSize() {
        return curLogFileSize;
    }

    public void setCurLogFileSize(long curLogFileSize) {
        this.curLogFileSize = curLogFileSize;
    }

    public long getNextWriteTimeStamp() {
        return nextWriteTimeStamp;
    }

    public void setNextWriteTimeStamp(long nextWriteTimeStamp) {
        this.nextWriteTimeStamp = nextWriteTimeStamp;
    }

    public long getCurCacheSize() {
        return curCacheSize;
    }

    public void setCurCacheSize(long curCacheSize) {
        this.curCacheSize = curCacheSize;
    }

    public String getLastWritedate() {
        return lastWritedate;
    }

    public void setLastWritedate(String lastWritedate) {
        this.lastWritedate = lastWritedate;
    }
}
