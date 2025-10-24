package com.zhuxi.user.module.application.service;

import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.infrastructure.properties.JwtProperties;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.utils.RedisUtils;
import com.zhuxi.user.module.domain.user.service.UserCacheService;
import com.zhuxi.user.module.infrastructure.config.DefaultProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    private final CacheKeyProperties commonKeys;
    private final RedisUtils redisUtils;


    @Override
    public void saveBlockList(String token, Role role,long expire) {
        try {
            if (Role.user.equals(role) || Role.Merchant.equals(role)) {
                redisUtils.ssSetValue(commonKeys.getUserPermissionKey() + token, "1",
                        expire, TimeUnit.MILLISECONDS);
            } else {
                redisUtils.ssSetValue(commonKeys.getBlockAdminTokenKey() + token, "1",
                        expire, TimeUnit.MILLISECONDS);
            }
        }catch (Exception e){
            log.error("save blockToken to redis---error:{}",e.getMessage());
            //待完善  重试或者告警等逻辑
        }
    }
}
