package com.zhuxi.common.applicaiton.cache;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.CacheMessage;
import com.zhuxi.common.shared.exception.cache.CacheException;
import com.zhuxi.common.shared.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Service
@Slf4j(topic = "CommonCacheServiceImpl")
public class CommonCacheServiceImpl implements CommonCacheService {
    private final CacheKeyProperties properties;
    private final RedisUtils redisUtils;
    private final DefaultRedisScript<Integer> unLockScript;

    public CommonCacheServiceImpl(CacheKeyProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.unLockScript = new DefaultRedisScript<>();
        ClassPathResource classPathResource = new ClassPathResource("lua/unLock.lua");
        ResourceScriptSource scriptSource = new ResourceScriptSource(classPathResource);
        unLockScript.setScriptSource(scriptSource);
                unLockScript.setResultType(Integer.class);
    }

    @Override
    public Long getUserIdBySn(String userSn) {
        Object id = redisUtils.hashGet(properties.getUserInfoKey() + userSn, "id");
        if (id == null){
            log.debug("未命中");
            return null;
        }
        if(id instanceof Integer iId){
            return Long.valueOf(iId);
        }else if(id instanceof Long lId){
            return lId;
        }

        log.debug("无匹配类型(Long,Integer),id:{}", id);
        return null;
    }

    @Override
    public Object soGetValue(String key) {
        return redisUtils.soGetValue(key);
    }

    @Override
    public void saveUserIdOrSn(String userSn, Long userId, Integer type) {
        if (type == 1){
            redisUtils.hashSet(properties.getUserInfoKey() + userSn, Map.of("id", userId));
        }
        else if (type == 0){
            redisUtils.hashSet(properties.getUseridMapSnKey(), Map.of(userId.toString(), userSn));
        }else if(type == 3){
            redisUtils.hashSet(properties.getUserInfoKey() + userSn, Map.of("id", userId));
            redisUtils.hashSet(properties.getUseridMapSnKey(), Map.of(userId.toString(), userSn));
        }
        else{
            throw new IllegalArgumentException("type参数格式不合法");
        }
    }

    @Override
    public void saveNullValue(String key) {
        redisUtils.ssSetValue(key, properties.getNULL_VALUE(), 2, TimeUnit.MINUTES);
    }

    @Override
    public void saveHashOneValue(String hashKey, String field, Object value) {
        redisUtils.hashSet(hashKey, Map.of(field, value));
    }

    @Override
    public Boolean getLock(String key, String value, long timeout, TimeUnit unit) {
        return redisUtils.ssSetValueIfAbsent(key, value, timeout, unit);
    }

    @Override
    public Boolean unLock(String key, String threadId) {
        Object result = redisUtils.executeLuaScript(
                unLockScript,
                List.of(key),
                List.of(threadId)
        );
        return (Integer) result == 1;
    }

    @Override
    public void delKey(String key) {
        Boolean b = redisUtils.deleteKey(key);
        if (!b){
            throw new CacheException(CacheMessage.DEL_KEY_ERROR);
        }
    }

    @Override
    public void delHashField(String hashKey, String field) {
        long result = redisUtils.delHashField(hashKey, field);
    }

    @Override
    public void hashFlushValue(Map<String, Object> map, String hashKey) {
        redisUtils.hashSet(hashKey, map);
    }

    @Override
    public Object getHashOneValue(String hashKey, String field) {
        return redisUtils.hashGet(hashKey, field);
    }

    @Override
    public String getUserSn(Long userId){
        if (userId == null){
            throw new CacheException(CacheMessage.ARGS_IS_NULL_OR_EMPTY);
        }
        String useridMapSnKey = properties.getUseridMapSnKey();
        // 获取id-sn映射
        Object seller = redisUtils.hashGet(useridMapSnKey, userId.toString());
        if (seller == null){
            return null;
        }
        return seller.toString();
    }

    // 检查缓存中是否存在NULL_VALUE标记 用于拦截重复无效不存在的sn的请求
    private void checkNullValue(String key){
        String result = redisUtils.ssGetValue(key);
        if (result == null){
            return;
        }

        if (result.equals(properties.getNULL_VALUE())) {
            throw new CacheException(CacheMessage.NOT_EXIST_DATA);
        }
    }

}
