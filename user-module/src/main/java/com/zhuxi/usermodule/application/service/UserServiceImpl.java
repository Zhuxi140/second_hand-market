package com.zhuxi.usermodule.application.service;


import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.constant.TokenMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.common.exception.TokenException;
import com.zhuxi.common.utils.BCryptUtils;
import com.zhuxi.usermodule.domain.user.enums.UserStatus;
import com.zhuxi.usermodule.domain.user.model.User;
import com.zhuxi.usermodule.domain.user.repository.UserRepository;
import com.zhuxi.usermodule.domain.user.service.UserService;
import com.zhuxi.usermodule.domain.user.valueObject.RefreshToken;
import com.zhuxi.usermodule.interfaces.dto.user.*;
import com.zhuxi.usermodule.interfaces.vo.user.UserViewVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    @Value("${refresh.expiration}")
    @Getter
    private int expiration;

    /**
     * 注册用户
     * 待完善: userSn的生成机制
    */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public User register(UserRegisterDTO register) {

        //检查用户名是否存在
        int exist = repository.checkUsernameExist(register.getUsername());
        if (exist == 1){
            throw new BusinessException(BusinessMessage.USERNAME_IS_EXIST);
        }

        User user = new User();
        user.register(register,bCryptUtils,RANDOM);

        //写入
        repository.save(user);

        return user;
    }

/**
     * 登录
     */

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public User login(UserLoginDTO login) {

        User user = repository.getLoginByUsername(login.getUsername());
        if (user.getUserStatus() == UserStatus.LOCKED){
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        // 构造长期令牌
        RefreshToken token = new RefreshToken(
                null,
                user.getId(),
                UUID.randomUUID().toString(),
                LocalDateTime.now().plusDays(expiration),
                null,
                null,
                null
        );
        repository.saveToken(token);

        boolean outCome = user.validateLogin(bCryptUtils,user,login,RANDOM,token);

        if (outCome){
            return user;
        }

        throw new BusinessException(BusinessMessage.USERNAME_OR_PASSWORD_ERROR);
    }

/*
*
     * 登出
     * 待完善 : 登出逻辑(redis)

*/
    @Override
    public void logout() {
    }

    /**
     * 更新用户资料
     * 待完善: 核心逻辑 、 检查userSn与 token中的标识 一致性
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateInfo(UserUpdateInfoDTO update, String userSn) {

        User user = repository.getUserIdRoleStatusBySn(userSn);
        if (user.getUserStatus() == UserStatus.LOCKED){
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        user.updateInfo(update);

        repository.save(user);
    }

    /*
     * 获取用户信息
     * 待完善: 检查userSn与 token中的标识 一致性
     */
    @Override
    @Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
    public UserViewVO getUserInfo(String userSn) {
        return repository.getUserInfo(userSn);
    }


    /*
     * 修改密码
     * 待完善: 检查userSn与 token中的标识一致性 、 改密后应强制拉黑token 使其重新登陆
    */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updatePassword(UserUpdatePwDTO updatePw, String userSn) {

        // 检查用户状态
        User user = repository.getUserIdRoleStatusBySn(userSn);
        if (user.getUserStatus() == UserStatus.LOCKED){
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        // 比对用户密码
        String newHash = user.changePassword(bCryptUtils, user, updatePw);

        //写入
        repository.updatePw(user.getId(),newHash);
    }

    /**
     * 刷新令牌
     * 待完善: 检查userSn与 token中的标识一致性
     */
    @Override
    @Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
    public RefreshToken renewJwt(RefreshDTO refresh) {

        Long userId = repository.getUserId(refresh.getUserSn());
        RefreshToken token = repository.getTokenByUserId(userId);
        if (!token.getTokenHash().equals(refresh.getRefreshToken())){
            log.warn("刷新令牌不一致");
            throw new TokenException(TokenMessage.LOGIN_INVALID);
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())){
            log.warn("刷新令牌已过期");
            throw new TokenException(TokenMessage.LOGIN_EXPIRED);
        }

        return token;
    }


}
