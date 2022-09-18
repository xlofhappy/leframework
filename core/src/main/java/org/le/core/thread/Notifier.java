package org.le.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

/**
 * Created by xiaole on 31/03/2017.
 *
 * @author xiaole
 */
public class Notifier {

    private static final Logger logger = LoggerFactory.getLogger(Notifier.class);

    /**
     * 4s、10s、10s
     */
    private int[] timeBlock = new int[]{2 * 1000, 5 * 1000, 10 * 1000};

    private final Semaphore semaphore;
    private final int total;

    public static Notifier one() {
        return new Notifier(Integer.MAX_VALUE);
    }

    public static Notifier one(int concurrentNumber) {
        return new Notifier(concurrentNumber);
    }

    public void startJob(Callback event) {
        startJob(event, null);
    }

    public void startJob(Callback event, Failure failure) {
        try {
            semaphore.acquire();
            ThreadExecutor.getExecutor().execute(() -> {
                int retry = 0;
                boolean loop;
                do {
                    try {
                        event.call();
                        loop = false;
                    } catch (Exception e) {
                        loop = true;
                        if (retry >= timeBlock.length) {
                            if (failure == null) {
                                logger.error(" a Notifier died. ", e);
                            } else {
                                failure.fail(e);
                            }
                            loop = false;
                        } else {
                            try {
                                Thread.sleep(timeBlock[retry++]);
                            } catch (InterruptedException ignored) {
                            }
                        }
                    } catch (Error error) {
                        loop = false;
                    }
                } while (loop);
                semaphore.release();
            });
        } catch (InterruptedException ignored) {
        }
    }

    public void await() {
        int i = semaphore.availablePermits();
        while (i < total) {
            try {
                Thread.sleep(1000);
                i = semaphore.availablePermits();
            } catch (Exception ignored) {
            }
        }
    }

    private Notifier(int n) {
        if (n < 0) {
            semaphore = new Semaphore(1);
            total = 1;
        } else {
            semaphore = new Semaphore(n);
            total = n;
            int corePoolSize = ThreadExecutor.getExecutor().getCorePoolSize();
            if (n > corePoolSize) {
                ThreadExecutor.getExecutor().setCorePoolSize(n);
            }
        }
    }

    private Notifier(int n, int[] timeBlocks) {
        if (n < 0) {
            semaphore = new Semaphore(1);
            total = 1;
        } else {
            semaphore = new Semaphore(n);
            total = n;
            int corePoolSize = ThreadExecutor.getExecutor().getCorePoolSize();
            if (n > corePoolSize) {
                ThreadExecutor.getExecutor().setCorePoolSize(n);
            }
        }

        if (timeBlocks != null) {
            for (int timeBlock : timeBlocks) {
                if (timeBlock < 0) {
                    throw new IllegalArgumentException("each block must not less than 0");
                }
            }
            timeBlock = timeBlocks;
        }
    }

}
