package com.zhuxi.user.module.application.service;


import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.CacheException;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.common.shared.utils.BCryptUtils;
import com.zhuxi.common.shared.utils.JwtUtils;
import com.zhuxi.user.module.application.service.process.user.ChangePasswordProcess;
import com.zhuxi.user.module.application.service.process.user.LoginProcess;
import com.zhuxi.user.module.application.service.process.user.RegisterProcess;
import com.zhuxi.user.module.domain.user.enums.UserStatus;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.domain.user.service.UserCacheService;
import com.zhuxi.user.module.domain.user.service.UserService;
import com.zhuxi.user.module.domain.user.model.RefreshToken;
import com.zhuxi.user.module.interfaces.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhuxi
 */
@Service
@RequiredArgsConstructor
@Slf4j
// 待完善接口 -- 修改手机号、头像上传处理逻辑等
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptUtils bCryptUtils;
    private final CacheKeyProperties properties;
    private final UserCacheService cache;
    private final JwtUtils jwtUtils;
    private final RegisterProcess registerProcess;
    private final LoginProcess loginProcess;
    private final ChangePasswordProcess changePasswordProcess;

    @Override
    public User register(UserRegisterDTO register) {
        //检查用户名是否存在
        registerProcess.checkNameExist(register);

        // 处理默认值
        String nickName = registerProcess.setDefaultNickName(register);

        //注册
        User user = registerProcess.buildUser(register, nickName,bCryptUtils,properties);

        //写入
        registerProcess.persistUserData(user);

        return user;
    }

    @Override
    public User login(UserLoginDTO login) {

        // 验证用户状态
        var user = loginProcess.checkUserStatus(login);

        // 比对用户密码
        boolean outcome = loginProcess.checkPassword(login, user, bCryptUtils);

        //登录
        loginProcess.login(outcome, user, properties);

        //异步缓存用户信息
        asyncSaveUserInfo( user.getId(), user.getUserSn());

        return user;
    }

    @Override
    public void logout(String userSn, String token) {
        token = token.replaceAll("(?i)Bearer\\s*", "");

        // 验证令牌 并获取剩余时间
        long expire = jwtUtils.validateToken(token, Role.user);

        // 验证长时token存在  若存在将其逻辑删除
        Long userId = repository.getUserId(userSn);
        Long id = repository.checkFreshTokenExist(userId);
        if (id != null) {
            repository.deleteToken(id);
        }

        // 将jwt-token写入redis黑名单 并清理相关用户缓存 待完善 后续相关业务缓存的清理
        cache.saveBlockList(token, Role.user, expire);
        CompletableFuture.runAsync(() -> {
            // 待完善  后续相关业务缓存的清理
            cache.deleteUserInfo(userSn);
        });
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateInfo(UserUpdateInfoDTO update, String userSn) {

        //从 缓存中获取用户信息
        List<String> keys = List.of("id", "userStatus", "role");
        User user = cache.getUserInfo(userSn,keys);

        // 验证用户状态
        if (user == null) {
            //未命中
            user = repository.getUserIdStatusBySn(userSn);
            User finalUser = user;
            // 异步缓存未命中信息
            CompletableFuture.runAsync(()->
                    cache.saveUserPartInfo(userSn, finalUser, keys)
            );
        }
        if (user.getUserStatus() == UserStatus.LOCKED) {
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        // 更新用户信息
        user.updateInfo(update);

        // 写入数据库
        repository.save(user);

        // 清除缓存
        cache.deleteUserInfo(userSn);

        // 事务提交后异步更新用户信息
        User finalUser1 = user;
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                CompletableFuture.runAsync(()-> {
                    User allUserInfo = null;
                    try {
                        int retryCount = 0;
                        while (retryCount < 2) {
                            allUserInfo = repository.getAllUserInfo(finalUser1.getId());
                            if (allUserInfo != null) {
                                break;
                            }
                            retryCount++;
                            Thread.sleep(100);
                        }
                        if (allUserInfo != null) {
                            cache.saveUserInfo(userSn, allUserInfo);
                        }else {
                            log.error("saveUserInfo_error : 查询出的allUserInfo为null ");
                            // 待完善  预警通知等
                        }
                    }catch (Exception  e){
                        log.error("saveUserInfo_error: {}", e.getMessage());
                    }
                });
            }
        });
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUserInfo(String userSn) {
        User user = cache.getUserInfo(userSn, null);
        if (user == null){
            //未命中
            user = repository.getUserInfo(userSn);
            if (user == null){
                log.error("getUserInfo_error : 获取用户信息失败");
                throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
            }
            //异步缓存用户信息
            asyncSaveUserInfo(user.getId(), user.getUserSn());
        }
        return user;
    }

    @Override
    public void updatePassword(UserUpdatePwDTO updatePw, String userSn, String token) {

        // 验证用户状态
        User user = changePasswordProcess.checkUserStatus(userSn);

        // 比对用户密码
        changePasswordProcess.validatePassword(bCryptUtils, updatePw, user);

        //异步清楚登录状态
        CompletableFuture.runAsync(() -> logout(userSn, token));

        //修改密码
        changePasswordProcess.updatePassword(updatePw, user, bCryptUtils);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public RefreshToken renewJwt(RefreshDTO refresh) {

        Long userId = (Long)cache.getOneInfo(refresh.getUserSn(), "id");
        if (userId == null) {
            //未命中
            userId = repository.getUserId(refresh.getUserSn());
        }

        RefreshToken token = repository.getTokenByUserId(userId);
        if (token == null){
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }
        if (!token.getTokenHash().equals(refresh.getRefreshToken())) {
            log.warn("刷新令牌不一致");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("刷新令牌已过期");
            throw new TokenException(AuthMessage.LOGIN_EXPIRED);
        }

        Integer id = (Integer)cache.getOneInfo(refresh.getUserSn(), "role");
        Role role;
        if (id == null){
            role = repository.getRole(userId);
        }else{
            role = Role.getRoleById(id);
        }
        token.setRole(role);


        //异步缓存用户角色信息
        Long finalUserId = userId;
        Role finalRole = role;
        CompletableFuture.runAsync(()-> {
            List<Object> values = List.of(
                    "id",
                    finalUserId,
                    "role",
                    finalRole.getId()
            );
            cache.saveUserPartInfo(refresh.getUserSn(),values);
        });
        return token;
    }

    private void asyncSaveUserInfo(Long userId,String userSn){
        CompletableFuture.runAsync(()-> {
                    User allUserInfo = null;
                    try {
                        int retryCount = 0;
                        while (retryCount < 2) {
                            allUserInfo = repository.getAllUserInfo(userId);
                            if (allUserInfo != null) {
                                break;
                            }
                            retryCount++;
                            Thread.sleep(100);
                        }
                        if (allUserInfo != null) {
                            cache.saveUserInfo(userSn, allUserInfo);
                        }else {

                            log.error("saveUserInfo_error : 查询出的allUserInfo为null。userSn:{}",userSn);
                            // 待完善  预警通知等
                        }
                    }catch (Exception  e){
                        log.error("saveUserInfo_error: {},userSn:{}", e.getMessage(), userSn);
                        throw new CacheException(e.getMessage());
                    }
                }
        );
    }

}
