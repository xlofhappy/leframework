package org.le.core.lock;

/**
 * @author xiaole
 */
public interface DistributedReentrantLock {

    void lock(String lockId);

    boolean tryLock(String lockId);

    void unlock(String lockId);
}
