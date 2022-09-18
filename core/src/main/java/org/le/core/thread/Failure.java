package org.le.core.thread;

/**
 * Created by xiaole on 12/05/2019.
 *
 * @author xiaole
 */
@FunctionalInterface
public interface Failure {

    /**
     * when failed, call
     * don't make too much work here
     */
    void fail(Throwable throwable);
}
