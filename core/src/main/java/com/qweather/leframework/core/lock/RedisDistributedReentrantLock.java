package com.qweather.leframework.core.lock;

import com.qweather.leframework.core.util.redis.RedisInstance;
import redis.clients.jedis.params.SetParams;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaole
 */
public class RedisDistributedReentrantLock implements DistributedReentrantLock {

    private static volatile RedisDistributedReentrantLock one;

    private final RedisInstance instance;
    private final Random random = new Random();

    private String uuid;

    private RedisDistributedReentrantLock(RedisInstance instance) {
        this.instance = instance;
    }

    public static RedisDistributedReentrantLock getInstance(RedisInstance instance) {
        if (one == null) {
            synchronized (RedisDistributedReentrantLock.class) {
                if (one == null) {
                    one = new RedisDistributedReentrantLock(instance);
                    one.uuid = UUID.randomUUID().toString();
                }
            }
        }
        return one;
    }

    @Override
    public void lock(String lockId) {
        acquireRedisLock(lockId, true);
    }

    @Override
    public boolean tryLock(String lockId) {
        return acquireRedisLock(lockId, false);
    }

    @Override
    public void unlock(String lockId) {
        releaseRedisLock(lockId);
    }

    private boolean acquireRedisLock(String lockId, boolean wait) {
        try {
            String value = uuid + ":" + Thread.currentThread() + ":" + Thread.currentThread().getId() + ":" + lockId;
            for (; ; ) {
                // use set not setnx, see https://redis.io/topics/distlock
                SetParams setParams = SetParams.setParams().nx().px(TimeUnit.SECONDS.toMillis(30));
                String response = instance.set(lockId, value, setParams);
                if (!"OK".equals(response)) {
                    if (value.equals(instance.get(lockId))) {
                        return true;
                    }
                    if (wait) {
                        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(10 + random.nextInt(10)));
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }

        return false;
    }

    private void releaseRedisLock(String lockId) {
        instance.del(lockId);
    }
}
