package com.practice.practicemanage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component // 配置为 spring 的 bean
public class RedisUtil {

    @Autowired
    private LogUtil logUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储数据到缓存
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        logUtil.info(RedisUtil.class, "存储数据到缓存");
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存储数据到缓存并设置过期时间
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        logUtil.info(RedisUtil.class, "存储数据到缓存并设置过期时间");
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 从缓存中获取数据
     * @param key
     * @return
     */
    public Object get(String key) {
        logUtil.info(RedisUtil.class, "从缓存中获取数据");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存中的数据
     * @param key
     */
    public void delete(String key) {
        logUtil.info(RedisUtil.class, "删除缓存中的数据");
        redisTemplate.delete(key);
    }

    /**
     * 判断缓存中是否存在某个 key
     * @param key
     * @return
     */
    public boolean exists(String key) {
        logUtil.info(RedisUtil.class, "判断缓存中是否存在某个 key");
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 更新缓存中的数据
     * @param key
     * @param value
     */
    public void update(String key, Object value) {
        logUtil.info(RedisUtil.class, "更新缓存中的数据");
        long timeout = 1; // 默认过期时间，单位：小时
        TimeUnit timeUnit = TimeUnit.HOURS;
        set(key, value, timeout, timeUnit);
    }

    /**
     * 更新缓存中的数据并设置过期时间
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void update(String key, Object value, long timeout, TimeUnit timeUnit) {
        logUtil.info(RedisUtil.class, "更新缓存中的数据并设置过期时间");
        set(key, value, timeout, timeUnit);
    }

}
