package com.educiot.recruit.common.util;

import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @program: server
 * @description: redis工具类
 * @author: XuChao
 * @create: 2020-04-29 16:08
 **/
@Log4j2
class RedisUtil {

    protected static String HOST = null;

    protected static int PORT = 0;

    protected static Integer DATABASE = 0;

    protected static String AUTH = null;

    protected static int MAX_ACTIVE = 0;

    protected static int MAX_IDLE = 0;

    protected static int MAX_WAIT = 0;

    protected static int TIMEOUT = 0;

    protected static boolean TEST_ON_BORROW = false;

    protected static JedisPool jedisPool = null;

    static {
        try {
            Properties pro = new Properties();
            InputStreamReader in = null;
            try {
                in = new InputStreamReader(RedisUtil.class.getResourceAsStream("/redis.properties"),
                        StandardCharsets.UTF_8);
                pro.load(in);
                HOST = pro.getProperty("host");
                PORT = Integer.parseInt(pro.getProperty("port"));
                DATABASE = Integer.parseInt(pro.getProperty("database"));
                AUTH = pro.getProperty("auth");
                MAX_ACTIVE = Integer.parseInt(pro.getProperty("active"));
                MAX_IDLE = Integer.parseInt(pro.getProperty("idle"));
                MAX_WAIT = Integer.parseInt(pro.getProperty("wait"));
                TIMEOUT = Integer.parseInt(pro.getProperty("timeout"));
                TEST_ON_BORROW = Boolean.parseBoolean(pro.getProperty("borrow"));
                log.info("redis配置文件读取成功：{host:" + HOST + ",port:" + PORT + "}");
            } catch (FileNotFoundException e) {
                log.error("找不到redis配置文件");
            } catch (IOException e) {
                log.error("redis配置文件IO异常");
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if (StringUtil.isNotEmpty(AUTH)) {
                jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH, DATABASE);
            } else {
                jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
            }

        } catch (Exception e) {
            log.error("初始化连接池失败:{}", e);
        }
    }

    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            }

            return null;
        } catch (Exception e) {
            log.error(" 获取Jedis实例失败:{}", e);
            throw e;
        }
        //return null;
    }

    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public static void hmSetWithTime(String key, Map<byte[], byte[]> paramMap, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key.getBytes(), paramMap);
            jedis.expire(key.getBytes(), seconds);
        } catch (Exception e) {
            log.error("往redis插入数据失败,key: {} and paramMap : {} and seconds : {}", new Object[]{key, paramMap,
                    Integer.valueOf(seconds), e});
        } finally {
            returnResource(jedis);
        }
    }

    public static void hmset(String key, Map<byte[], byte[]> paramMap) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(key.getBytes(), paramMap);
        } catch (Exception e) {
            log.error("往redis插入数据失败,key: {} and paramMap : {}", new Object[]{key, paramMap, e});
        } finally {
            returnResource(jedis);
        }
    }

    public static List<String> hmGet(String key, String... fields) {
        Jedis jedis = null;
        List valueList = null;
        try {
            jedis = getJedis();
            List<byte[]> bytes = new ArrayList<>();
            for (String field :
                    fields) {
                bytes.add(field.getBytes());
            }
            valueList = jedis.hmget(key.getBytes(), (byte[][]) bytes.toArray());
        } catch (Exception e) {
            log.error("读取redis数据失败,key: {} and fields : {}", new Object[]{key, fields, e});
        } finally {
            returnResource(jedis);
        }
        return valueList;
    }

    public static long getValidTime(String key) {
        Jedis jedis = null;
        long validTime = 0L;
        try {
            jedis = getJedis();
            validTime = jedis.ttl(key.getBytes()).longValue();
        } catch (Exception e) {
            log.error("获取redis-key有效时间失败,key: {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return validTime;
    }

    public static void hSetWithTime(String key, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key.getBytes(), seconds);
        } catch (Exception e) {
            log.error("设置string数据类型数据，并设置有效时间,key: {},value: {},seconds: {}", new Object[]{key, value,
                    Integer.valueOf(seconds), e});
        } finally {
            returnResource(jedis);
        }
    }

    public static Long hdel(String[] keys) {
        Jedis jedis = null;
        long delKeyNumber = 0L;
        try {
            jedis = getJedis();
            delKeyNumber = jedis.del(keys).longValue();
        } catch (Exception e) {
            log.error("根据key删除redis中的数据失败,keys: {}", keys, e);
        } finally {
            returnResource(jedis);
        }
        log.info("根据key删除redis中的数据成功,keys: {} and delKeyNumber: {}", keys.toString(), Long.valueOf(delKeyNumber));
        return Long.valueOf(delKeyNumber);
    }

    /**
     * 根据key和具体field删除
     *
     * @param key    key值
     * @param fields 具体要删除的
     * @return java.lang.Long
     * @author Pan Juncai
     * @date 2020/5/8 16:33
     */
    public static Long delHSet(String key, String... fields) {
        Jedis jedis = null;
        long delKeyNumber = 0L;
        try {
            jedis = getJedis();
//            for (field)
            List<byte[]> bytes = new ArrayList<byte[]>();
            for (String field : fields) {
                bytes.add(field.getBytes());
            }
            delKeyNumber = jedis.hdel(key.getBytes(), (byte[][]) bytes.toArray());
        } catch (Exception e) {
            log.error("根据key和fields删除redis中的数据失败,key: {}，fields：{}", key, fields, e);
        } finally {
            returnResource(jedis);
        }
        log.info("根据key和fields删除redis中的数据成功,key: {}，fields：{} and delKeyNumber: {}", key, fields, delKeyNumber);
        return delKeyNumber;
    }

    public static Object get(String key) {
        Jedis jedis = null;
        Object value = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.get(key.getBytes());
            if (result != null) {
                value = SerializeUtil.deserialize(result);
            }
        } catch (Exception e) {
            log.error("获取string类型数据失败,key: {}", key );
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    public static Boolean exists(String key) {
        Jedis jedis = null;
        boolean value = false;
        try {
            jedis = getJedis();
            value = jedis.exists(key.getBytes());

        } catch (Exception e) {
            log.error("exists,key: {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    public static Long exists(String... keys) {
        Jedis jedis = null;
        Long value = 0L;
        try {
            jedis = getJedis();
            value = jedis.exists(keys);

        } catch (Exception e) {
            log.error("exists,key: {}", keys, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    public static Map<byte[], byte[]> hgetAll(String key) {
        Jedis jedis = null;
        Map<byte[], byte[]> result = null;
        try {
            jedis = getJedis();
            result = jedis.hgetAll(key.getBytes());
        } catch (Exception e) {
            log.error("hgetAll数据失败,key: {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public static byte[] hGet(byte[] key, byte[] field) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = getJedis();
            value = jedis.hget(key, field);

        } catch (Exception e) {
            log.error("hget,key: {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }


    public static Object hGet(String key, String field) {
        Jedis jedis = null;
        Object value = null;
        try {
            jedis = getJedis();
            byte[] result = jedis.hget(key.getBytes(), field.getBytes());
            if (result != null) {
                value = SerializeUtil.deserialize(result);
                // value = new String(result);
            }
        } catch (Exception e) {
            log.error("hget,key: {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    public static void hSet(String key, String field, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception e) {
            log.error("hSet,key: {},value: {}", new Object[]{key, value, e});
        } finally {
            returnResource(jedis);
        }
    }

    public static void hSetWithTime(String key, String field, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key.getBytes(), seconds);
        } catch (Exception e) {
            log.error("hSetWithTime,key: {},value: {}", new Object[]{key, value, e});
        } finally {
            returnResource(jedis);
        }
    }

    public static void set(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.set(key.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception e) {
            log.error("设置string数据类型数据,key: {},value: {}", new Object[]{key, value, e});
        } finally {
            returnResource(jedis);
        }
    }


    public static void main(String[] args) {

        RedisUtil.set("11", "SADSADSA");
        System.out.println(RedisUtil.get("11"));


    }

}
