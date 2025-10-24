package com.zhuxi.common.shared.utils;

import com.zhuxi.common.shared.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Slf4j
@Component
public class RedisUtils {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ValueOperations<String,Object> soValueOperations;
    private final ZSetOperations<String,Object> zSetOperations;
    private final HashOperations<String,String,Object> hashOperations;
    private final ValueOperations<String, String> ssValueOperations;
    private final StringRedisTemplate sRedisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.sRedisTemplate = stringRedisTemplate;
        this.soValueOperations = redisTemplate.opsForValue();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.hashOperations = redisTemplate.opsForHash();
        this.ssValueOperations = stringRedisTemplate.opsForValue();
    }


    public String ssGetValue(String key) {
        return ssValueOperations.get(key);
    }

    public void ssSetValue(String key, String value, long expireTime, TimeUnit timeUnit){
        ssValueOperations.set(key,value, expireTime, timeUnit);
    }

    public <T> T soGetValue(String key,Class<T> type) {
        Object value = soValueOperations.get(key);
        return type.cast(value);
    }

    public <T> void soSetExValue(String key, T value, long expireTime, TimeUnit timeUnit){
        soValueOperations.set(key,value, expireTime, timeUnit);
    }



}
