package com.zhuxi.common.applicaiton.service;

import com.zhuxi.common.domain.repository.PermissionCacheRepository;
import com.zhuxi.common.domain.service.PermissionCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.infrastructure.properties.JwtProperties;
import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.interfaces.result.PermissionInfoOne;
import com.zhuxi.common.shared.constant.RedisMessage;
import com.zhuxi.common.shared.exception.CacheException;
import com.zhuxi.common.shared.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionCacheServiceImpl implements PermissionCacheService {

    private final RedisUtils redisUtils;
    private final CacheKeyProperties keys;
    private final PermissionCacheRepository repository;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PermissionInfo getPermissionInfo(String userSn) {

        // 获取用户ID 待完善 : 加入缓存逻辑
        Long userId = repository.getUserId(userSn);
        String key = keys.getUserPermissionKey() + userSn;
        PermissionInfo permissionInfo = redisUtils.soGetValue(key, PermissionInfo.class);
        if (isValidCachedInfo(permissionInfo, userId)){
            log.debug("hit the cache");
            return permissionInfo;
        }

        log.debug("miss the cache. userId:{}",userId);
        PermissionInfo permissionInfo1 = loadDataBase(userId);

        // 异步保存缓存
        asyncSaveCache(key,permissionInfo1);

        return permissionInfo1;
    }

    /**
     * 验证缓存信息是否有效
     */
    private boolean isValidCachedInfo(PermissionInfo cachedInfo, Long userId) {
        return cachedInfo != null
                && cachedInfo.getUserId() != null
                && cachedInfo.getUserId().equals(userId)
                && cachedInfo.getPermissionCode() != null;
    }

    /**
     * 从数据库加载权限信息
     */
    private PermissionInfo loadDataBase(Long userId){
    try {
        // 获取用户权限、角色权限以及用户角色
        CompletableFuture<List<String>> userPermissionFuture = CompletableFuture
                .supplyAsync(() -> repository.getUserPermissionInfo(userId));
        CompletableFuture<List<PermissionInfoOne>> rolePermissionFuture = CompletableFuture
                .supplyAsync(() -> repository.getRolePermissionInfo(userId));

        // 合并权限列表
        List<String> userPermissionCodes = userPermissionFuture.get();
        List<PermissionInfoOne> rolePermissionInfo = rolePermissionFuture.get();
        for (PermissionInfoOne permissionInfoOne : rolePermissionInfo){
            userPermissionCodes.add(permissionInfoOne.getPermissionCode());
        }
        PermissionInfo allPermissionInfo = new PermissionInfo(userId, rolePermissionInfo.get(0).getRole(), userPermissionCodes);

        //去除重复权限
        PermissionInfo permissionInfo = delDuplicates(allPermissionInfo);

        permissionInfo.setUserId(userId);
        return permissionInfo;
    }catch (Exception e){
        log.error("load data base error. userId:{}",userId,e);
        return new PermissionInfo(userId, null, new ArrayList<>());
    }
    }

    /**
     * 去除重复权限
     */
    private PermissionInfo delDuplicates(PermissionInfo allPermissionInfo){
        Set<String> mergeCodes =  new HashSet<>();
        if (allPermissionInfo != null && allPermissionInfo.getPermissionCode() != null){
            mergeCodes.addAll(allPermissionInfo.getPermissionCode());
        }

        return new PermissionInfo(null, null, new ArrayList<>(mergeCodes));
    }

    /**
     * 异步保存权限信息到缓存
     * 待完善 ： 存入缓存如果失败 的重试机制 以及最终仍不成功 的发送告警逻辑
     */
    private void asyncSaveCache(String key,PermissionInfo permissionInfo){
        CompletableFuture.runAsync(()->{
            Long userExpire = jwtProperties.getUserExpire();
            try {
                redisUtils.soSetExValue(key, permissionInfo, userExpire, TimeUnit.SECONDS);
            }catch (Exception e){
                log.error("asyncSaveCache:soSetValue error.e:{}\n key:{},value:{},expireTime:{},timeUnit:{}",e.getMessage(),key,permissionInfo,userExpire,TimeUnit.SECONDS);
            }
        });
    }
}
