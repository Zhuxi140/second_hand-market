package com.zhuxi.user.module.application.service;


import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.constant.AuthMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.common.exception.TokenException;
import com.zhuxi.common.utils.BCryptUtils;
import com.zhuxi.user.module.application.command.RegisterCommand;
import com.zhuxi.user.module.domain.user.enums.UserStatus;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
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
import java.util.List;
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
    private final DefaultProperties defaultProperties;

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

        // 处理默认值
        String nickName = null;
        if (register.getNickname() == null || register.getNickname().isBlank()){
            nickName = "换换" + RANDOM.nextInt(10000);
        }else{
            nickName = register.getNickname();
        }
        // 加密密码
        String hashPassword = bCryptUtils.hashCode(register.getPassword());

        //注册
        User user = new User();
        RegisterCommand registerCommand = new RegisterCommand(register, defaultProperties, hashPassword, nickName);
        user.register(registerCommand);

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

        // 验证用户状态
        User user = repository.getLoginByUsername(login.getUsername());
        user.checkUserStatus();

        // 比对用户密码
        String hashPassword = user.getPassword();
        boolean outcome = bCryptUtils.checkPw(
                hashPassword == null ? VIRTUAL_HASH.hash.get(RANDOM.nextInt(VIRTUAL_HASH.hash.size())) : login.getPassword(),
                hashPassword
        );

        if (outcome){
            // 构造长期令牌
            RefreshToken token = new RefreshToken(
                    null,
                    user.getId(),
                    UUID.randomUUID().toString(),
                    LocalDateTime.now().plusDays(defaultProperties.getRefreshExpire()),
                    null,
                    null,
                    null
            );
            repository.saveToken(token);
            user.login(token);

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
     */
    @Override
    @Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
    public UserViewVO getUserInfo(String userSn) {
        return repository.getUserInfo(userSn);
    }


    /*
     * 修改密码
    */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updatePassword(UserUpdatePwDTO updatePw, String userSn) {

        // 检查用户状态
        User user = repository.getUserIdRoleStatusBySn(userSn);
        user.checkUserStatus();

        // 比对用户密码
        boolean result = bCryptUtils.checkPw(updatePw.getOldPassword(), user.getPassword());
        if (!result){
            throw new BusinessException(BusinessMessage.USER_OLD_PASSWORD_ERROR);
        }
        String hashPassword = bCryptUtils.hashCode(updatePw.getNewPassword());

        //写入
        repository.updatePw(user.getId(),hashPassword);

        // 登出
        logout();
    }

    /**
     * 刷新令牌
     */
    @Override
    @Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
    public RefreshToken renewJwt(RefreshDTO refresh) {

        Long userId = repository.getUserId(refresh.getUserSn());
        RefreshToken token = repository.getTokenByUserId(userId);
        if (!token.getTokenHash().equals(refresh.getRefreshToken())){
            log.warn("刷新令牌不一致");
            throw new TokenException(AuthMessage.LOGIN_INVALID);
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())){
            log.warn("刷新令牌已过期");
            throw new TokenException(AuthMessage.LOGIN_EXPIRED);
        }

        return token;
    }


    private static class VIRTUAL_HASH{
        public static List<String> hash = List.of(
                "$2a$10$o0d2OaDqaXmnhJDw4934Guo17bVhKafy5PnwwkySttZJd1JiEBJbC",
                "$2a$10$zQSBxnXcy.Xh1kG1YFWwSO.VLm1DBeX1VB8yxjmHOKz5KUASWPWx.",
                "$2a$10$Yj9Jfl.sJXZiUjuWu94etePa9u.ZPYJBLEDPP8yNt4YHO/CIkyIxq",
                "$2a$10$jzjviFMYJA8COLaCg6Boieh0e1.Q.Y1AhPlomT8RC25sc1IuB6qQ.",
                "$2a$10$GG6r.eAdUNqxslQSAOHyTOZDk/qUCB3RLCUAzSriYfZkNOIqrASeC",
                "$2a$10$C8PO6p4iq8rnJhV0sYpNNO9WLj96hSp5tmTEPQfLtyf9hS4e/or3W"
        );
    }

}
