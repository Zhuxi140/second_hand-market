package com.zhuxi.common.applicaiton.cache;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhuxi
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "CommonCacheServiceImpl")
public class CommonCacheServiceImpl implements CommonCacheService {
    private final CacheKeyProperties properties;
    private final RedisUtils redisUtils;

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
}
