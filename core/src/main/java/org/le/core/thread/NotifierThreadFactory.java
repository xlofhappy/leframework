package org.le.core.thread;

import com.google.common.base.Strings;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Notifier Thread Factory
 *
 * @author xiaole
 */
public class NotifierThreadFactory implements ThreadFactory {

    private final  ThreadGroup   group;
    private final  AtomicInteger threadNumber  = new AtomicInteger(1);
    private static ThreadFactory threadFactory = null;

    public static ThreadFactory getInstance() {
        if ( threadFactory == null ) {
            synchronized (NotifierThreadFactory.class) {
                if ( threadFactory == null ) {
                    threadFactory = new NotifierThreadFactory();
                }
            }
        }
        return threadFactory;
    }

    private NotifierThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(@Nullable Runnable r) {
        String namePrefix = "Notifier-";
        String name          = namePrefix + threadNumber.getAndIncrement();
        name = Strings.padEnd(name, 13, ' ');
        Thread t          = new Thread(group, r, name, 0);
        if ( t.isDaemon() ) {
            t.setDaemon(false);
        }
        if ( t.getPriority() != Thread.NORM_PRIORITY ) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
