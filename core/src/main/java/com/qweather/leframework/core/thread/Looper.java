package com.qweather.leframework.core.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 应用于需要重试的同步执行代码
 * 如果接口访问超时后，再次进行重试，直至超出重试设定阀值
 */
public class Looper {

    private static final Logger logger = Logger.getLogger(Looper.class.getName());

    private final int timesOfLoop;
    private final int gapsMillis;

    private Looper(int timesOfLoop, int gapsMillis) {
        this.timesOfLoop = timesOfLoop;
        this.gapsMillis = gapsMillis;
    }


    /**
     * @param timesOfLoop 循环次数
     * @param gapsMillis  发生异常后的循环间隔时间
     */
    public static Looper instance(int timesOfLoop, int gapsMillis) {
        return new Looper(timesOfLoop, gapsMillis);
    }

    /**
     * 不抛出异常的循环
     */
    public void loopQuietly(Callback event) {
        try {
            loopQuietly(event, null);
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }

    public void loopQuietly(Callback event, Failure failure) {
        try {
            loop(event, failure, true);
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }

    public void loop(Callback event) throws Exception {
        loop(event, null, false);
    }

    public void loop(Callback event, Failure failure) throws Exception {
        loop(event, failure, false);
    }

    public void loop(Callback event, Failure failure, boolean swallowIOException) throws Exception {
        int retry = 0;
        boolean loop = true;
        do {
            try {
                event.call();
                loop = false;
            } catch (Exception e) {
                if (retry++ >= timesOfLoop) {
                    if (failure != null) {
                        failure.fail(e);
                    }
                    if (swallowIOException) {
                        logger.log(Level.WARNING, "Exception thrown while looping.", e);
                    } else {
                        throw e;
                    }
                    loop = false;
                } else {
                    try {
                        Thread.sleep(gapsMillis);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        } while (loop);
    }

}