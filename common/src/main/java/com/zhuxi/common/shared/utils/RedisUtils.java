package com.zhuxi.common.shared.utils;

import com.zhuxi.common.shared.constant.CacheMessage;
import com.zhuxi.common.shared.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
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

    /**
     * 获取RedisTemplate(string,string)中的值
     * @param key 键
     * @return 值
     */
    public String ssGetValue(String key) {
        return ssValueOperations.get(key);
    }

    /**
     * 设置RedisTemplate(string,string)中的值
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间
     * @param timeUnit 时间单位
     */
    public void ssSetValue(String key, String value, long expireTime, TimeUnit timeUnit){
        ssValueOperations.set(key,value, expireTime, timeUnit);
    }

    /**
     * 获取redisTemplate(string,object)中的值
     * @param key 键
     * @return 值
     */
    public Object soGetValue(String key) {
        return soValueOperations.get(key);
    }

    /**
     * 设置redisTemplate(string,object)中的值
     * @param key 键
     * @param value 值
     * @param expireTime 过期时间
     * @param timeUnit 时间单位
     * @param <T> 值类型
     */
    public <T> void soSetExValue(String key, T value, long expireTime, TimeUnit timeUnit){
        soValueOperations.set(key,value, expireTime, timeUnit);
    }

    /**
     * 获取HashRedisTemplate(String,String,object)中的值
     * @param key 键
     * @param value 值
     */
    public void hashSet(String key, Map<String,?> value){
        hashOperations.putAll(key, value);
    }

    /**
     * 获取HashRedisTemplate(String,String,object)中所有字段的值
     * @param key 键
     * @param value 值
     */
    public List<Object> hashGetAll(String key, List<String> value){
        return hashOperations.multiGet(key, value);

    }

    /**
     * 获取HashRedisTemplate(String,String,object)中的具体key字段对应的值
     * @param hashKey 键
     * @param key 值
     * @return 值
     */
    public Object hashGet(String hashKey, String key){
        return hashOperations.get(hashKey, key);
    }

    /**
     * 删除key中的值
     * @param key 键
     * @return 删除结果
     */
    public Boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }

    public Object executeLuaScript(DefaultRedisScript<?> script, List<String> keys, List<?> args){
        Object execute = redisTemplate.execute(
                script,
                keys,
                args.toArray()
        );
        if (execute == null){
            throw new CacheException(CacheMessage.LUA_EXECUTE_ERROR + "return is null");
        }else if(execute instanceof Long){
            if (((Long) execute) == 0){
                throw new CacheException(CacheMessage.LUA_EXECUTE_ERROR + "return is 0");
            }
        }
        return execute;
    }



}
