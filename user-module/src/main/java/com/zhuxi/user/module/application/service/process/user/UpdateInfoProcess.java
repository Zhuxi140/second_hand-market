package com.zhuxi.user.module.application.service.process.user;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.domain.user.service.UserCacheService;
import com.zhuxi.user.module.interfaces.dto.user.UserUpdateInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhuxi
 */

@Slf4j(topic = "UpdateInfoProcess")
@Component
@RequiredArgsConstructor
public class UpdateInfoProcess {

    private final UserRepository repository;

    /**
     * 获取用户基础信息
     * @param userSn 用户编号
     * @param cache 缓存服务
     * @param commonCache 通用缓存服务
     * @param properties 缓存key
     * @return 用户基础信息
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUserInfo(String userSn, UserCacheService cache,
                            CommonCacheService  commonCache, CacheKeyProperties properties){
        List<String> keys = List.of("id", "userStatus", "role");
        User user = cache.getUserInfo(userSn, keys);
        if (user == null) {
            //未命中
            user = repository.getUserIdStatusBySn(userSn);
            if(user == null){
                commonCache.saveNullValue(properties.getUserInfoKey() + userSn);
                throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
            }
            User finalUser = user;
            // 异步缓存未命中信息
            CompletableFuture.runAsync(()->
                    cache.saveUserPartInfo(userSn, finalUser, keys)
            );
        }
        return user;
    }

    /**
     * 保存更新用户信息
     * @param user 用户信息
     * @param update 更新信息
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void saveUpdate(User user, UserUpdateInfoDTO update){
        // 更新用户信息
        user.updateInfo(update);
        // 写入数据库
        repository.save(user);
    }
}
