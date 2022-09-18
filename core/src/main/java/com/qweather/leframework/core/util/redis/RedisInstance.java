package com.qweather.leframework.core.util.redis;

import com.qweather.leframework.core.properties.LePropertyRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.util.SafeEncoder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaole
 */
public class RedisInstance {

    private final Logger logger = LoggerFactory.getLogger(RedisInstance.class);

    private final JedisPool jp;
    private final int maxActive;

    public RedisInstance(JedisPool jp, LePropertyRedis lePropertyRedis) {
        this.jp = jp;
        this.maxActive = lePropertyRedis.getMaxActive();
    }

    private String convert(byte[] data) {
        if (data == null) {
            return null;
        } else {
            return SafeEncoder.encode(data);
        }
    }

    /**
     * 获取 jedis 实例
     *
     * @return Jedis
     */
    private Jedis getJedis() {
        return jp.getResource();
    }

    /**
     * 获取 jedis 实例
     *
     * @return Jedis
     */
    public String redisInfo() {
        JedisPool jedisPool = jp;
        return "num waiters: " + jedisPool.getNumWaiters() + " num active: " + jedisPool.getNumActive() + " num idle: " + jedisPool.getNumIdle();
    }

    public long ttl(String key) {
        return ttl(SafeEncoder.encode(key));
    }

    public long ttl(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.ttl(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return -1;
    }

    /**
     * 设置值
     * 将key设置值为value，如果key不存在，这种情况下等同SET命令
     * 当key存在时，什么也不做。SETNX是”SET if Not eXists”的简写。
     *
     * @param key   redis key
     * @param value 对象
     * @return true : key 不存在 | false : key 已存在
     */
    public boolean setnx(byte[] key, byte[] value) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.setnx(key, value) == 1;
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return false;
    }

    public boolean setnx(String key, String object) {
        return setnx(SafeEncoder.encode(key), SafeEncoder.encode(object));
    }

    /**
     * 设置 值
     *
     * @param key    redis key
     * @param object 对象
     */
    public String setex(String key, String object, int expireSecond) {
        return setex(SafeEncoder.encode(key), SafeEncoder.encode(object), expireSecond);
    }

    public String setex(byte[] key, byte[] object, int expireSecond) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.setex(key, expireSecond, object);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 设置 值
     *
     * @param key   redis key
     * @param value 对象
     */
    public void set(String key, String value) {
        set(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    public String set(final String key, final String object, final SetParams setParams) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.set(key, object, setParams);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 设置 值
     *
     * @param key  redis key
     * @param data byte数组
     */
    public void set(byte[] key, byte[] data) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.set(key, data);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 通过管道 导入数据
     *
     * @param value collection
     */
    public void setByPipeline(Map<String, String> value) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<String, String> stringEntry : value.entrySet()) {
                    pipeline.set(stringEntry.getKey(), stringEntry.getValue());
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void setByPipeline2(Map<byte[], byte[]> value) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<byte[], byte[]> stringEntry : value.entrySet()) {
                    pipeline.set(stringEntry.getKey(), stringEntry.getValue());
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 通过管道 导入数据
     *
     * @param value        collection
     * @param expireSecond expire second
     */
    public void setexByPipeline(Map<String, String> value, int expireSecond) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<String, String> stringEntry : value.entrySet()) {
                    pipeline.setex(stringEntry.getKey(), expireSecond, stringEntry.getValue());
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void setexByPipeline2(Map<byte[], byte[]> value, int expireSecond) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<byte[], byte[]> stringEntry : value.entrySet()) {
                    pipeline.setex(stringEntry.getKey(), expireSecond, stringEntry.getValue());
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public String get(String key) {
        byte[] bytes = get(SafeEncoder.encode(key));
        return bytes != null ? SafeEncoder.encode(bytes) : null;
    }

    public byte[] get(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.get(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public List<String> mget(String... keys) {
        return mget(Arrays.stream(keys).map(SafeEncoder::encode).toArray(byte[][]::new)).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<byte[]> mget(byte[]... keys) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.mget(keys);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 获取 值
     *
     * @param key redis key
     * @return clazz
     */
    public List<String> getByPipeline(List<String> key) {
        return getByPipeline2(key.stream().map(SafeEncoder::encode).collect(Collectors.toList())).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<byte[]> getByPipeline2(List<byte[]> key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (int i = 0; i < key.size(); i++) {
                    pipeline.get(key.get(i));
                }
                return pipeline.syncAndReturnAll().stream().map(e -> e == null ? null : (byte[]) e).collect(Collectors.toList());
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * list 新增
     * 务必保证集合内的对象均为同一数据类型
     *
     * @param key    redis key
     * @param object 对象
     */
    public void listAdd(String key, List<String> object) {
        listAdd(SafeEncoder.encode(key), object.stream().map(SafeEncoder::encode).toArray(byte[][]::new));
    }

    public void listAdd(byte[] key, byte[]... object) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                if (object != null) {
                    jedis.rpush(key, object);
                }
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * list  获取
     *
     * @param key redis key
     */
    public List<String> getList(String key) {
        return getList(SafeEncoder.encode(key)).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<byte[]> getList(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                Long length = jedis.llen(key);
                return jedis.lrange(key, 0, length - 1);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * list  获取 长度
     *
     * @param key redis key
     */
    public long llen(String key) {
        return llen(SafeEncoder.encode(key));
    }

    public long llen(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.llen(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return 0L;
    }

    /**
     * list 左侧出队, 队列减少一个对象
     *
     * @param key redis key
     */
    public String lpop(String key) {
        return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
    }

    public byte[] lpop(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.lpop(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * list 左侧出队, 队列减少一个对象
     *
     * @param key redis key
     * @return 入队后, 队列长度
     */
    public long lpush(String key, String value) {
        return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    public long lpush(byte[] key, byte[] value) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.lpush(key, value);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return 0L;
    }

    /**
     * list 右侧出队, 队列减少一个对象
     *
     * @param key redis key
     */
    public String rpop(String key) {
        return SafeEncoder.encode(rpop(SafeEncoder.encode(key)));
    }

    public byte[] rpop(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.rpop(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * list 右侧入队, 队列增加一个对象
     *
     * @param key redis key
     * @return 入队后，队列长度
     */
    public long rpush(String key, String value) {
        return rpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    public long rpush(byte[] key, byte[] value) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.rpush(key, value);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return 0L;
    }

    /**
     * 设置队列指定下标的值
     *
     * @param key   redis key
     * @param index 下标
     * @param value 值
     */
    public void lset(String key, long index, String value) {
        lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
    }

    public void lset(byte[] key, long index, byte[] value) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.lset(key, index, value);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }


    /**
     * 哈希表中对应字段 中是否存在指定字段
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @return boolean
     */
    public boolean hexists(String key, String field) {
        return hexists(SafeEncoder.encode(key), SafeEncoder.encode(field));
    }

    public boolean hexists(byte[] key, byte[] field) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hexists(key, field);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return false;
    }

    /**
     * 哈希表中字段个数
     *
     * @param key redis key
     * @return long
     */
    public long hlen(String key) {
        return hlen(SafeEncoder.encode(key));
    }

    public long hlen(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hlen(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return 0L;
    }

    /**
     * 获取 哈希表中对应字段的 值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @return clazz
     */
    public String hget(String key, String field) {
        return SafeEncoder.encode(hget(SafeEncoder.encode(key), SafeEncoder.encode(field)));
    }

    public byte[] hget(byte[] key, byte[] field) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hget(key, field);
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
        return null;
    }

    /**
     * 获取 哈希表中对应字段的 值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @return clazz
     */
    public List<String> hgetByPipeline(List<String> key, List<String> field) {
        return hgetByPipeline2(key.stream().map(SafeEncoder::encode).collect(Collectors.toList()), field.stream().map(SafeEncoder::encode).collect(Collectors.toList())).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<byte[]> hgetByPipeline2(List<byte[]> key, List<byte[]> field) {
        if (key.size() == field.size()) {
            byte loop = 0;
            do {
                try (Jedis jedis = getJedis()) {
                    Pipeline pipeline = jedis.pipelined();
                    List<byte[]> result = new ArrayList<>();
                    for (int i = 0; i < key.size(); i++) {
                        pipeline.hget(key.get(i), field.get(i));
                    }
                    return pipeline.syncAndReturnAll().stream().map(e -> (byte[]) e).collect(Collectors.toList());
                } catch (Exception ignored) {
                    loop++;
                }
            } while (loop < maxActive);
        }
        return null;
    }

    /**
     * 获取 哈希表中对应字段的 值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @return clazz
     */
    public List<String> hmgetString(String key, List<String> field) {
        return hmget(key, field);
    }

    public List<byte[]> hmgetString(byte[] key, byte[] field) {
        return hmget(key, field);
    }

    /**
     * 获取 哈希表中对应字段的 值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @return clazz
     */
    public List<String> hmget(String key, List<String> field) {
        return hmget(SafeEncoder.encode(key), field.stream().map(SafeEncoder::encode).toArray(byte[][]::new)).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<byte[]> hmget(byte[] key, byte[]... field) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hmget(key, field);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 获取 哈希表中对应字段的 值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @param value java对象
     */
    public void hset(String key, String field, String value) {
        hset(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
    }

    public void hset(byte[] key, byte[] field, byte[] value) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.hset(key, field, value);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hscan(key, cursor);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hscan(key, cursor);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public ScanResult<String> scan(String cursor) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.scan(cursor);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public ScanResult<String> scan(String cursor, ScanParams scanParams) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.scan(cursor, scanParams);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public ScanResult<byte[]> scan(byte[] cursor) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.scan(cursor);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public ScanResult<byte[]> scan(byte[] cursor, ScanParams scanParams) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.scan(cursor);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 通过管道 批量设置哈希表中对应字段的值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     * @param value java对象
     */
    public void hsetByPipeline(List<String> key, List<String> field, List<String> value) {
        hsetByPipeline2(key.stream().map(SafeEncoder::encode).collect(Collectors.toList()), field.stream().map(SafeEncoder::encode).collect(Collectors.toList()), value.stream().map(SafeEncoder::encode).collect(Collectors.toList()));
    }

    public void hsetByPipeline2(List<byte[]> key, List<byte[]> field, List<byte[]> value) {
        if (field.size() == value.size() && key.size() == field.size()) {
            byte loop = 0;
            boolean doLoop = true;
            do {
                try (Jedis jedis = getJedis()) {
                    Pipeline pipeline = jedis.pipelined();
                    for (int i = 0; i < field.size(); i++) {
                        pipeline.hset(key.get(i), field.get(i), value.get(i));
                    }
                    pipeline.sync();
                    doLoop = false;
                } catch (Exception ignored) {
                    loop++;
                }
            } while (doLoop && loop < maxActive);
        }
    }

    /**
     * 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。
     * 如果字段已存在，该操作无效果。
     *
     * @param key   key
     * @param field hash field key
     * @param value hash value
     * @return boolean true 操作成功 | false 操作失败
     */
    public boolean hsetnx(String key, String field, String value) {
        return hsetnx(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
    }

    public boolean hsetnx(byte[] key, byte[] field, byte[] value) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hsetnx(key, field, value) == 1;
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return false;
    }

    /**
     * 获取 哈希表中所有的 key
     *
     * @param key redis key
     * @return Set<String>
     */
    public Set<String> hkeys(String key) {
        return hkeys(SafeEncoder.encode(key)).stream().map(SafeEncoder::encode).collect(Collectors.toSet());
    }

    public Set<byte[]> hkeys(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hkeys(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 获取 哈希表中所有的 value
     *
     * @param key redis key
     * @return List<String>
     */
    public List<String> hvalues(String key) {
        return hkeys(SafeEncoder.encode(key)).stream().map(SafeEncoder::encode).collect(Collectors.toList());
    }

    public List<byte[]> hvalues(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.hvals(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 获取 哈希表中对应字段的 值
     *
     * @param key   redis key
     * @param field hashmap 中指定字段
     */
    public void hdel(String key, String field) {
        hdel(SafeEncoder.encode(key), SafeEncoder.encode(field));
    }

    public void hdel(byte[] key, byte[] field) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.hdel(key, field);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 通过管道批量执行 删除哈希表中对应字段
     *
     * @param keys  redis keys
     * @param field hashmap 中指定字段
     */
    public void hdelByPipeline(List<String> keys, List<String> field) {
        hdelByPipeline2(keys.stream().map(SafeEncoder::encode).collect(Collectors.toList()), field.stream().map(SafeEncoder::encode).collect(Collectors.toList()));
    }

    public void hdelByPipeline2(List<byte[]> keys, List<byte[]> field) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (int i = 0; i < keys.size(); i++) {
                    pipeline.hdel(keys.get(i), field.get(i));
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 删除 key
     *
     * @param key redis key
     */
    public void delByPipeline(List<String> key) {
        delByPipeline(key.stream().map(SafeEncoder::encode).toArray(byte[][]::new));
    }

    public void delByPipeline(byte[]... key) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (int i = 0; i < key.length; i++) {
                    pipeline.del(key[i]);
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 删除 key
     *
     * @param key redis key
     */
    public void del(String key) {
        del(SafeEncoder.encode(key));
    }

    public void del(byte[] key) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.del(key);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public Long incr(String key) {
        return incr(SafeEncoder.encode(key));
    }

    public Long incr(byte[] key) {
        return incrBy(key, 1L);
    }

    public Long incrBy(String key, Integer step) {
        return incrBy(SafeEncoder.encode(key), step);
    }

    public Long incrBy(byte[] key, Integer step) {
        return incrBy(key, step.longValue());
    }

    /**
     * 自增 key
     *
     * @param key redis key
     */
    public Long incrBy(String key, Long step) {
        return incrBy(SafeEncoder.encode(key), step);
    }

    public Long incrBy(byte[] key, Long step) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                if (step == null) {
                    step = 1L;
                }
                if (step == 1L) {
                    return jedis.incr(key);
                } else {
                    return jedis.incrBy(key, step);
                }
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public void incrByPipeline(Map<String, Integer> map) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (String s : map.keySet()) {
                    pipeline.incrBy(s, map.get(s));
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void incrByPipeline2(Map<byte[], Integer> map) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (byte[] s : map.keySet()) {
                    pipeline.incrBy(s, map.get(s));
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public Long decr(String key) {
        return decr(SafeEncoder.encode(key));
    }

    public Long decr(byte[] key) {
        return decrBy(key, 1);
    }

    public Long decrBy(String key, Integer step) {
        return decrBy(SafeEncoder.encode(key), step);
    }

    public Long decrBy(byte[] key, Integer step) {
        return decrBy(key, step.longValue());
    }

    public Long decrBy(String key, Long step) {
        return decrBy(SafeEncoder.encode(key), step);
    }

    public Long decrBy(byte[] key, Long step) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                if (step == null) {
                    step = 1L;
                }
                if (step == 1L) {
                    return jedis.decr(key);
                } else {
                    return jedis.decrBy(key, step);
                }
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 设置/刷新 过期时间
     *
     * @param key     redis key
     * @param timeout 过期时间 秒
     */
    public void expire(String key, int timeout) {
        expire(SafeEncoder.encode(key), timeout);
    }

    public void expire(byte[] key, int timeout) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.expire(key, timeout);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void expireByPipeline(Map<String, Integer> map) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (String s : map.keySet()) {
                    pipeline.expire(s, map.get(s));
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void expireByPipeline2(Map<byte[], Integer> map) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (byte[] s : map.keySet()) {
                    pipeline.expire(s, map.get(s));
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 检查 key 是否存在
     *
     * @param key redis key
     * @return boolean
     */
    public boolean exists(String key) {
        return exists(SafeEncoder.encode(key));
    }

    public boolean exists(byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.exists(key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return false;
    }

    /**
     * 获取缓存中全部的 key
     * 不建议使用，会锁定key
     *
     * @param pattern 匹配
     * @return Set<String>
     */
    public Set<String> keys(String pattern) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.keys(pattern);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 将key重命名为newkey，如果key与newkey相同，将返回一个错误。如果newkey已经存在，则值将被覆盖
     *
     * @param newKey 新的 key
     * @param oldKey 旧的 key
     */
    public void rename(String oldKey, String newKey) {
        rename(SafeEncoder.encode(oldKey), SafeEncoder.encode(newKey));
    }

    public void rename(byte[] oldKey, byte[] newKey) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.rename(oldKey, newKey);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param keys   keys 集合
     * @param args   参数 集合
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object eval(String script, List<String> keys, List<String> args) {
        return eval(SafeEncoder.encode(script), keys.stream().map(SafeEncoder::encode).collect(Collectors.toList()), args.stream().map(SafeEncoder::encode).collect(Collectors.toList()));
    }

    public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.eval(script, keys, args);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param keys   keys 集合
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object eval(String script, List<String> keys) {
        return eval(SafeEncoder.encode(script), keys.stream().map(SafeEncoder::encode).toArray(byte[][]::new));
    }

    public Object eval(byte[] script, byte[]... keys) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.eval(script, keys.length, keys);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param key    key
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object eval(String script, String key) {
        return eval(SafeEncoder.encode(script), SafeEncoder.encode(key));
    }

    public Object eval(byte[] script, byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.eval(script, 1, key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script   脚本
     * @param keyCount key的数量(指 params 前几位为 key，之后的为参数）
     * @param params   key 和参数的集合
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object eval(String script, int keyCount, String... params) {
        return eval(SafeEncoder.encode(script), keyCount, Arrays.stream(params).map(SafeEncoder::encode).toArray(byte[][]::new));
    }

    public byte[] eval(byte[] script, int keyCount, byte[]... params) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return (byte[]) jedis.eval(script, keyCount, params);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param key    key
     * @param args   参数
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object eval(String script, String key, String args) {
        return eval(SafeEncoder.encode(script), SafeEncoder.encode(key), SafeEncoder.encode(args));
    }

    public Object eval(byte[] script, byte[] key, byte[] args) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.eval(script, 1, key, args);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object eval(String script) {
        return eval(SafeEncoder.encode(script));
    }

    public byte[] eval(byte[] script) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return (byte[]) jedis.eval(script);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本的 key
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object evalsha(String script) {
        return evalsha(SafeEncoder.encode(script));
    }

    public Object evalsha(byte[] script) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.evalsha(script);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本的 key
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object evalsha(String script, String key) {
        return evalsha(SafeEncoder.encode(script), SafeEncoder.encode(key));
    }

    public Object evalsha(byte[] script, byte[] key) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.evalsha(script, 1, key);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script   脚本的 key
     * @param keyCount key的数量(指 params 前几位为 key，之后的为参数）
     * @param params   key 和参数的集合
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object evalsha(String script, int keyCount, String... params) {
        return evalsha(SafeEncoder.encode(script), keyCount, Arrays.stream(params).map(SafeEncoder::encode).toArray(byte[][]::new));
    }

    public Object evalsha(byte[] script, int keyCount, byte[]... params) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.evalsha(script, keyCount, params);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本的 key
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object evalsha(String script, String key, String args) {
        return evalsha(SafeEncoder.encode(script), SafeEncoder.encode(key), SafeEncoder.encode(args));
    }

    public Object evalsha(byte[] script, byte[] key, byte[] args) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.evalsha(script, 1, key, args);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本的 key
     * @param keys   key的集合
     * @param args   参数的集合
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object evalsha(String script, List<String> keys, List<String> args) {
        return evalsha(SafeEncoder.encode(script), keys.stream().map(SafeEncoder::encode).collect(Collectors.toList()), args.stream().map(SafeEncoder::encode).collect(Collectors.toList()));
    }

    public Object evalsha(byte[] script, List<byte[]> keys, List<byte[]> args) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.evalsha(script, keys, args);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param keys   key的集合
     * @return lua脚本返回结果转成的 redis 结果
     */
    public Object evalsha(String script, List<String> keys) {
        return evalsha(SafeEncoder.encode(script), keys.stream().map(SafeEncoder::encode).toArray(byte[][]::new));
    }

    public Object evalsha(byte[] script, byte[]... keys) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.evalsha(script, keys.length, keys);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 检查脚本是否存在
     *
     * @param sha1 脚本的 key
     * @return boolean
     */
    public boolean scriptExists(String sha1) {
        return scriptExists(SafeEncoder.encode(sha1));
    }

    public boolean scriptExists(byte[] sha1) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.scriptExists(sha1) == 1L;
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return false;
    }

    /**
     * 加载脚本
     *
     * @param script 脚本
     * @return String 脚本的 key
     */
    public String scriptLoad(String script) {
        return SafeEncoder.encode(scriptLoad(SafeEncoder.encode(script)));
    }

    public byte[] scriptLoad(byte[] script) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.scriptLoad(script);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 添加geo对象
     *
     * @param key       redis key
     * @param longitude 经度
     * @param latitude  纬度
     * @param member    名称
     */
    public void geoadd(String key, double longitude, double latitude, String member) {
        geoadd(SafeEncoder.encode(key), longitude, latitude, SafeEncoder.encode(member));
    }

    public void geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.geoadd(key, longitude, latitude, member);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 添加geo对象
     *
     * @param key       redis key
     * @param longitude 经度
     * @param latitude  纬度
     * @param member    名称
     */
    public void geoaddByPipeline(List<String> key, List<Double> longitude, List<Double> latitude, List<String> member) {
        geoaddByPipeline2(key.stream().map(SafeEncoder::encode).collect(Collectors.toList()), longitude, latitude, member.stream().map(SafeEncoder::encode).collect(Collectors.toList()));
    }

    public void geoaddByPipeline2(List<byte[]> key, List<Double> longitude, List<Double> latitude, List<byte[]> member) {
        if (key.size() == longitude.size() && key.size() == latitude.size() && key.size() == member.size()) {
            byte loop = 0;
            boolean doLoop = true;
            do {
                try (Jedis jedis = getJedis()) {
                    Pipeline pipeline = jedis.pipelined();
                    for (int i = 0; i < key.size(); i++) {
                        pipeline.geoadd(key.get(i), longitude.get(i), latitude.get(i), member.get(i));
                    }
                    pipeline.sync();
                    doLoop = false;
                } catch (Exception ignored) {
                    loop++;
                }
            } while (doLoop && loop < maxActive);
        }
    }

    /**
     * 以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素
     *
     * @param key       redis key
     * @param longitude 经度
     * @param latitude  纬度
     * @param radius    半径
     * @param unit      单位 m 表示单位为米。 km 表示单位为千米。 mi 表示单位为英里。 ft 表示单位为英尺
     * @param param     WITHDIST: 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致
     *                  WITHCOORD: 将位置元素的经度和维度也一并返回
     *                  WITHHASH: 以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。 这个选项主要用于底层应用或者调试， 实际中的作用并不大
     *                  ASC: 根据中心的位置， 按照从近到远的方式返回位置元素
     *                  DESC: 根据中心的位置， 按照从远到近的方式返回位置元素
     * @return List<GeoRadiusResponse>
     */
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
                                             GeoRadiusParam param) {
        return georadius(SafeEncoder.encode(key), longitude, latitude, radius, unit, param);
    }

    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit,
                                             GeoRadiusParam param) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.georadius(key, longitude, latitude, radius, unit, param);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 添加有序集合对象
     *
     * @param key    redis key
     * @param score  评分
     * @param member 名称
     */
    public void zadd(String key, double score, String member) {
        zadd(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
    }

    public void zadd(byte[] key, double score, byte[] member) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.zadd(key, score, member);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 添加有序集合对象
     *
     * @param key    redis key
     * @param score  评分
     * @param member 名称
     */
    public void zaddByPipeline(List<String> key, List<Double> score, List<String> member) {
        zaddByPipeline2(key.stream().map(SafeEncoder::encode).collect(Collectors.toList()), score, member.stream().map(SafeEncoder::encode).collect(Collectors.toList()));
    }

    public void zaddByPipeline2(List<byte[]> key, List<Double> score, List<byte[]> member) {
        if (key.size() == score.size() && key.size() == member.size()) {
            byte loop = 0;
            boolean doLoop = true;
            do {
                try (Jedis jedis = getJedis()) {
                    Pipeline pipeline = jedis.pipelined();
                    for (int i = 0; i < key.size(); i++) {
                        pipeline.zadd(key.get(i), score.get(i), member.get(i));
                    }
                    pipeline.sync();
                    doLoop = false;
                } catch (Exception ignored) {
                    loop++;
                }
            } while (doLoop && loop < maxActive);
        }
    }

    /**
     * 根据评分的范围获取结果集
     *
     * @param key    redis key
     * @param min    最低分
     * @param max    最高分
     * @param offset 偏移量
     * @param count  取出总数
     * @return Set
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return zrangeByScore(SafeEncoder.encode(key), min, max, offset, count).stream().map(SafeEncoder::encode).collect(Collectors.toSet());
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.zrangeByScore(key, min, max, offset, count);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    /**
     * 返回存储在有序集合key中的指定范围的元素。 返回的元素可以认为是按得分从最低到最高排列。 如果得分相同，将按字典排序。
     * 参数start和stop都是基于零的索引，即0是第一个元素，1是第二个元素，以此类推。 它们也可以是负数，表示从有序集合的末尾的偏移量，其中-1是有序集合的最后一个元素，-2是倒数第二个元素，等等。
     *
     * @param key
     * @param start
     */
    public Set<String> zrange(String key, long start, long end) {
        return zrange(SafeEncoder.encode(key), start, end).stream().map(SafeEncoder::encode).collect(Collectors.toSet());
    }

    public Set<byte[]> zrange(byte[] key, long start, long end) {
        Jedis jedis = null;
        Set<byte[]> zrange = null;
        try {
            jedis = getJedis();
            zrange = jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error(" redis zrange error, ", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return zrange;
    }

    /**
     * 给指定的成员增加评分
     *
     * @param key    redis key
     * @param score  增加的分数
     * @param member 成员
     */
    public void zincrby(String key, double score, String member) {
        zincrby(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
    }

    public void zincrby(byte[] key, double score, byte[] member) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                jedis.zincrby(key, score, member);
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void zincrbyPipeline(Map<String, Map<String, Integer>> z) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<String, Map<String, Integer>> entry : z.entrySet()) {
                    Map<String, Integer> value = entry.getValue();
                    for (Map.Entry<String, Integer> integerEntry : value.entrySet()) {
                        pipeline.zincrby(entry.getKey(), integerEntry.getValue(), integerEntry.getKey());
                    }
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void zincrbyPipeline2(Map<byte[], Map<byte[], Integer>> z) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<byte[], Map<byte[], Integer>> entry : z.entrySet()) {
                    Map<byte[], Integer> value = entry.getValue();
                    for (Map.Entry<byte[], Integer> integerEntry : value.entrySet()) {
                        pipeline.zincrby(entry.getKey(), integerEntry.getValue(), integerEntry.getKey());
                    }
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    /**
     * 获取指定的成员的评分
     *
     * @param key    redis key
     * @param member 成员
     * @return Double : may be null
     */
    public Double zscore(String key, String member) {
        return zscore(SafeEncoder.encode(key), SafeEncoder.encode(member));
    }

    public Double zscore(byte[] key, byte[] member) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.zscore(key, member);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public void hIncrbyPipeline(Map<String, Map<String, Integer>> z) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<String, Map<String, Integer>> entry : z.entrySet()) {
                    Map<String, Integer> value = entry.getValue();
                    for (Map.Entry<String, Integer> integerEntry : value.entrySet()) {
                        pipeline.hincrBy(entry.getKey(), integerEntry.getKey(), integerEntry.getValue());
                    }
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public void hIncrbyPipeline2(Map<byte[], Map<byte[], Integer>> z) {
        byte loop = 0;
        boolean doLoop = true;
        do {
            try (Jedis jedis = getJedis()) {
                Pipeline pipeline = jedis.pipelined();
                for (Map.Entry<byte[], Map<byte[], Integer>> entry : z.entrySet()) {
                    Map<byte[], Integer> value = entry.getValue();
                    for (Map.Entry<byte[], Integer> integerEntry : value.entrySet()) {
                        pipeline.hincrBy(entry.getKey(), integerEntry.getKey(), integerEntry.getValue());
                    }
                }
                pipeline.sync();
                doLoop = false;
            } catch (Exception ignored) {
                loop++;
            }
        } while (doLoop && loop < maxActive);
    }

    public Long zrem(byte[] key, byte[]... members) {
        byte loop = 0;
        do {
            try (Jedis jedis = getJedis()) {
                return jedis.zrem(key, members);
            } catch (Exception ignored) {
                loop++;
            }
        } while (loop < maxActive);
        return null;
    }

    public Long zrem(String key, String... members) {
        return zrem(SafeEncoder.encode(key), Arrays.stream(members).map(SafeEncoder::encode).toArray(byte[][]::new));
    }
}
