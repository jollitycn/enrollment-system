package com.educiot.recruit.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;


/**
 * @program: server
 * @description: redis工具类
 * @author: XuChao
 * @create: 2020-04-29 16:08
 **/
@Log4j2
public class JSONRedisUtil {

    private static List<String> hmGet(String key, String... fields) {
        return RedisUtil.hmGet(key, fields);
    }

    private static long getValidTime(String key) {
        return RedisUtil.getValidTime(key);
    }

    public static void hSetWithTime(String key, Object value, int seconds) {
        RedisUtil.hSetWithTime(key, JSON.toJSONString(value), seconds);
    }

    private static Long hdel(String... keys) {
        return RedisUtil.hdel(keys);
    }

    /**
     * @param key
     * @param fields
     * @return
     */
    private static Long delHSet(String key, String... fields) {
        return RedisUtil.delHSet(key, fields);
    }

    public static Long delHSet(String key, String fields) {
        return RedisUtil.delHSet(key, fields);
    }



//    /**
//     * @param key
//     * @param field
//     * @return
//     */
//    public static Object hget(String key, String field) {
//        return RedisUtil.hget(key, field);
//    }
//    public static <T> T hget(byte[] key, byte[] field, Class<T> clazz) {
//        byte[] obj = RedisUtil.hget(key, field);
//        if (obj != null) {
//            return JSON.parseObject(obj, clazz);
//        } else {
//            return null;
//        }
//    }

    public static <T> List<T> parseArray(String key, Class<T> clazz) {
        return JSON.parseArray(RedisUtil.get(key).toString(), clazz);
    }

    public static <T> T parse(String key, Class<T> clazz) {
        Object obj = RedisUtil.get(key);
        if (obj != null) {
            return JSON.parseObject(obj.toString(), clazz);
        } else {
            return null;
        }
    }


    /**
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T hg(String key, String field, Class<T> clazz) {
        Object obj = RedisUtil.hGet(key, field);
        if (obj != null) {
            return JSON.parseObject(obj.toString(), clazz);
        } else {
            return null;
        }
    }

    public static Boolean exists(String key) {
        return RedisUtil.exists(key);
    }

    /**
     * @param keys
     * @return
     */
    public static Long exists(String... keys) {
        return RedisUtil.exists(keys);
    }

    /**
     * @param key
     * @return
     */
    public static Map<byte[], byte[]> hgetAll(String key) {
        return RedisUtil.hgetAll(key);
    }



    public static void hSet(String key, String field, Object value) {
        RedisUtil.hSet(key, field, JSON.toJSONString(value));
    }

    public static void hSetWithTime(String key, String field, Object value, int seconds) {
        RedisUtil.hSetWithTime(key, field, JSON.toJSONString(value), seconds);
    }

    public static void set(String key, Object value) {
        RedisUtil.set(key, JSON.toJSONString(value));
    }


    private static void main(String[] args) {
        JSONRedisUtil.set("11", "SADSADSA");
        System.out.println(JSONRedisUtil.parse("11", String.class));
        System.out.println(JSONRedisUtil.hg("11","22", String.class));
    }
}
