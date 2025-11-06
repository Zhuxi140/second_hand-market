package com.zhuxi.common.applicaiton.service;

import com.zhuxi.common.domain.repository.PermissionCacheRepository;
import com.zhuxi.common.domain.service.PermissionCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.infrastructure.properties.JwtProperties;
import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.interfaces.result.PermissionInfoOne;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.utils.JackSonUtils;
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

    /**
     * 获取用户权限信息
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PermissionInfo getPermissionInfo(String userSn) {

        // 获取用户ID 待完善 : 加入缓存逻辑
        Long userId = repository.getUserId(userSn);
        String key = keys.getUserPermissionKey() + userSn;
        PermissionInfo permissionInfo = JackSonUtils.convert(redisUtils.soGetValue(key), PermissionInfo.class);
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
     * 检查JwtToken是否在黑名单中
     */
    @Override
    public boolean checkBlackToken(String token, Role role) {
        if (Role.user.equals(role) || Role.Merchant.equals(role)){
            return redisUtils.ssGetValue(keys.getBlockUserTokenKey() + token) != null;
        }else{
            return redisUtils.ssGetValue(keys.getBlockAdminTokenKey() + token) != null;
        }
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
        CompletableFuture<List<String>> banedPermissionFuture = CompletableFuture
                .supplyAsync(() -> repository.getBanedPermissionInfo(userId));
        CompletableFuture<List<PermissionInfoOne>> rolePermissionFuture = CompletableFuture
                .supplyAsync(() -> repository.getRolePermissionInfo(userId));

        // 合并权限列表
        List<String> baned = banedPermissionFuture.get();
        List<PermissionInfoOne> rolePermissionInfo = rolePermissionFuture.get();

        //去除重复权限
        PermissionInfo permissionInfo = removeBanedPermission(baned,rolePermissionInfo);

        permissionInfo.setUserId(userId);
        return permissionInfo;
    }catch (Exception e){
        log.error("load data base error. userId:{}",userId,e);
        return new PermissionInfo(userId, null, new ArrayList<>());
    }
    }

    /**
     * 去除被禁用权限
     */
    private PermissionInfo removeBanedPermission(List<String> baned,List<PermissionInfoOne> rolePermission){
        Set<String> banedSet =  new HashSet<>(baned);

        List<String> list = rolePermission.stream()
                .map(PermissionInfoOne::getPermissionCode)
                .filter(permissionCode -> !banedSet.contains(permissionCode))
                .toList();

        return new PermissionInfo(null, rolePermission.get(1).getRole().getId(), new ArrayList<>(list));
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
