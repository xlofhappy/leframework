package com.qweather.leframework.core.factory;

import com.qweather.leframework.core.properties.LePropertyRedis;
import com.qweather.leframework.core.util.redis.RedisInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis tools
 * Created by xiaole on 15/11/11.
 *
 * @author xiaole
 */
public final class RedisInstanceFactory {

    private final Logger logger = LoggerFactory.getLogger(RedisInstanceFactory.class);

    private static RedisInstanceFactory factory;
    private final List<RedisInstance> instances = new ArrayList<>(3);

    public RedisInstanceFactory(LePropertyRedis lePropertyRedis) {
        if (lePropertyRedis != null) {
            boolean flag = lePropertyRedis.isUsable();
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
            int maxIdle = lePropertyRedis.getMaxIdle();
            // 控制一个pool最少有多少个状态为idle(空闲的)的jedis实例。
            int minIdle = lePropertyRedis.getMinIdle();
            // 可用连接实例的最大数目，默认值为8； 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            int maxActive = lePropertyRedis.getMaxActive();
            String[] addr = lePropertyRedis.getAddr();
            int[] port = lePropertyRedis.getPort();
            String[] auth = lePropertyRedis.getAuth();
            int[] db = lePropertyRedis.getDb();
            // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
            int maxWait = lePropertyRedis.getMaxWait();
            int timeout = lePropertyRedis.getTimeout();

            if (flag) {
                if (addr.length == 0) {
                    logger.error("redis connection address param is null");
                    throw new RuntimeErrorException(new Error("redis address param [ addr ] error"));
                }
                if (port.length == 0) {
                    logger.error("redis connection port param is error");
                    throw new RuntimeErrorException(new Error("redis address param [ port ] error"));
                }
                if (auth.length == 0) {
                    logger.error("redis connection auth is error");
                    throw new RuntimeErrorException(new Error("redis address param [ auth ] error"));
                }
                if (db.length == 0) {
                    logger.error("redis connection db is error");
                    throw new RuntimeErrorException(new Error("redis address param [ db ] error"));
                }
                if (addr.length != port.length || port.length != auth.length || auth.length != db.length) {
                    throw new RuntimeErrorException(new Error("redis address param error"));
                } else {
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(maxActive);
                    config.setMaxIdle(maxIdle);
                    config.setMinIdle(minIdle);
                    config.setMaxWaitMillis(maxWait);
                    config.setTestOnBorrow(false);
                    config.setTestWhileIdle(false);
                    for (int i = 0; i < addr.length; i++) {
                        JedisPool jedisPool;
                        if (auth[i] == null || "".equals(auth[i].trim())) {
                            jedisPool = new JedisPool(config, addr[i], port[i], timeout, null, db[i]);
                        } else {
                            jedisPool = new JedisPool(config, addr[i], port[i], timeout, auth[i], db[i]);
                        }
                        instances.add(new RedisInstance(jedisPool, lePropertyRedis));
                    }
                }
            }
        }
        factory = this;
    }

    /**
     * 获取 RedisInstance 实例
     */
    public RedisInstance getInstance(int index) {
        if (index >= 0 && index < factory.instances.size()) {
            return factory.instances.get(index);
        } else {
            return null;
        }
    }

    /**
     * 是否存在 RedisInstance 实例
     */
    public boolean existsInstance() {
        return !factory.instances.isEmpty();
    }


}
