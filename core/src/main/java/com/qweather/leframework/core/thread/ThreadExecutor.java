package com.qweather.leframework.core.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程执行器
 *
 * @author xiaole
 */
public class ThreadExecutor {

    private static ThreadPoolExecutor threadPoolExecutor = null;

    public static ThreadPoolExecutor getExecutor() {
        if ( threadPoolExecutor == null ) {
            synchronized (ThreadExecutor.class) {
                if ( threadPoolExecutor == null ) {
                    /*
                     * 线程池保留线程数
                     */
                    int corePoolSize = 20;
                    /*
                     * 线程池 并行执行的 数量限制
                     */
                    int poolSize = 2000;
                    /*
                     * 线程池线程空闲时间 180 秒
                     */
                    int keepAliveTime = 60;
                    /*
                     * 最大排队数量
                     */
                    int maxPoolSize = 500;
                    /*
                     * 1） ThreadPoolExecutor.AbortPolicy 丢弃任务，并抛出 RejectedExecutionException 异常。
                     * 2） ThreadPoolExecutor.CallerRunsPolicy：该任务被线程池拒绝，由调用 execute方法的线程执行该任务。
                     * 3） ThreadPoolExecutor.DiscardOldestPolicy ： 抛弃队列最前面的任务，然后重新尝试执行任务。
                     * 4） ThreadPoolExecutor.DiscardPolicy，丢弃任务，不过也不抛出异常。
                     */
                    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, poolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>(maxPoolSize), NotifierThreadFactory.getInstance(), new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return threadPoolExecutor;
    }
}
