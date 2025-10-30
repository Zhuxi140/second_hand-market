package com.zhuxi.user.module.application.service;


import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.common.shared.utils.BCryptUtils;
import com.zhuxi.common.shared.utils.JwtUtils;
import com.zhuxi.user.module.application.service.Process.LoginProcess;
import com.zhuxi.user.module.application.service.Process.RegisterProcess;
import com.zhuxi.user.module.domain.user.enums.UserStatus;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.domain.user.service.UserCacheService;
import com.zhuxi.user.module.domain.user.service.UserService;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.infrastructure.config.DefaultProperties;
import com.zhuxi.user.module.interfaces.dto.user.*;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
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
    private final DefaultProperties defaultProperties;
    private final UserCacheService userCacheService;
    private final JwtUtils jwtUtils;
    private final RegisterProcess registerProcess;
    private final LoginProcess loginProcess;

    /**
     * 注册用户
     */
    @Override
    public User register(UserRegisterDTO register) {
        //检查用户名是否存在
        registerProcess.checkNameExist(register);

        // 处理默认值
        String nickName = registerProcess.setDefaultNickName(register);

        //注册
        User user = registerProcess.buildUser(register, nickName,bCryptUtils,defaultProperties);

        //写入
        registerProcess.persistUserData(user);

        return user;
    }

    /**
     * 登录
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public User login(UserLoginDTO login) {

        // 验证用户状态
        var user = loginProcess.checkUserStatus(login);

        // 比对用户密码
        boolean outcome = loginProcess.checkPassword(login, user, bCryptUtils);

        //登录
        loginProcess.login(outcome, user, defaultProperties);

        return user;
    }

    /*
     * 登出
     */
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

        // 将jwt-token写入redis黑名单
        userCacheService.saveBlockList(token, Role.user, expire);
    }

    /**
     * 更新用户资料
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateInfo(UserUpdateInfoDTO update, String userSn) {

        User user = repository.getUserIdRoleStatusBySn(userSn);
        if (user.getUserStatus() == UserStatus.LOCKED) {
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        user.updateInfo(update);

        repository.save(user);
    }

    /*
     * 获取用户信息
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserViewVO getUserInfo(String userSn) {
        return repository.getUserInfo(userSn);
    }


    /*
     * 修改密码
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updatePassword(UserUpdatePwDTO updatePw, String userSn, String token) {

        // 检查用户状态
        User user = repository.getUserIdRoleStatusBySn(userSn);
        user.checkUserStatus();

        // 比对用户密码
        boolean result = bCryptUtils.checkPw(updatePw.getOldPassword(), user.getPassword());
        if (!result) {
            throw new BusinessException(BusinessMessage.USER_OLD_PASSWORD_ERROR);
        }

        // 异步清除用户登录状态
        CompletableFuture.runAsync(() -> logout(userSn, token));

        String hashPassword = bCryptUtils.hashCode(updatePw.getNewPassword());
        //写入新密码
        repository.updatePw(user.getId(), hashPassword);
    }

    /**
     * 刷新令牌
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public RefreshToken renewJwt(RefreshDTO refresh) {

        Long userId = repository.getUserId(refresh.getUserSn());
        RefreshToken token = repository.getTokenByUserId(userId);
        if (!token.getTokenHash().equals(refresh.getRefreshToken())) {
            log.warn("刷新令牌不一致");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("刷新令牌已过期");
            throw new TokenException(AuthMessage.LOGIN_EXPIRED);
        }

        // 获取角色
        Role role = repository.getRole(userId);
        token.setRole(role);

        return token;
    }




}
