package org.le.core.thread;

public class Looper {

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

    public void loop(Callback event, Failure failure) throws Exception {
        int retry = 0;
        boolean loop;
        do {
            try {
                event.call();
                loop = false;
            } catch (Exception e) {
                loop = true;
                if (retry >= timesOfLoop) {
                    if (failure == null) {
                        throw e;
                    } else {
                        failure.fail(e);
                    }
                    loop = false;
                } else {
                    try {
                        Thread.sleep(gapsMillis);
                    } catch (InterruptedException ignored) {
                    }
                }
            } catch (Error error) {
                loop = false;
            }
        } while (loop);
    }

}
