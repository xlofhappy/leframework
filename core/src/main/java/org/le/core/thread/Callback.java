package org.le.core.thread;

/**
 * Created by xiaole on 31/03/2017.
 *
 * @author xiaole
 */
@FunctionalInterface
public interface Callback {

    /**
     * call
     *
     * @throws RuntimeException runtime exception
     */
    void call() throws Exception;
}
