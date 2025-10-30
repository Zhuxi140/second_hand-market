package com.zhuxi.user.module.application.service.Process;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.utils.BCryptUtils;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.infrastructure.config.DefaultProperties;
import com.zhuxi.user.module.interfaces.dto.user.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
public class LoginProcess {

    private final UserRepository userRepository;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    /**
     * 检查用户状态
     * @param login 登录信息
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User checkUserStatus(UserLoginDTO login) {
        User user = userRepository.getLoginByUsername(login.getUsername());
        user.checkUserStatus();
        return user;
    }

    /**
     * 检查密码
     * @param login 登录信息
     * @param user 用户信息
     * @param bCryptUtils BCryptUtils
     * @return 密码是否正确
     */
    public boolean checkPassword(UserLoginDTO login, User user, BCryptUtils bCryptUtils){
        String hashPassword = user.getPassword();
        return  bCryptUtils.checkPw(
                hashPassword == null ? LoginProcess.VIRTUAL_HASH.hash.get(RANDOM.nextInt(LoginProcess.VIRTUAL_HASH.hash.size())) : hashPassword ,
                login.getPassword()
        );
    }

    /**
     * 登录
     * @param outcome 登录结果
     * @param user 用户信息
     * @param defaultProperties 默认配置
     */
    @Transactional(rollbackFor = Exception.class)
    public void login(boolean outcome, User user, DefaultProperties defaultProperties) {
        if (outcome) {
            RefreshToken freshToken = userRepository.getTokenByUserId(user.getId());
            RefreshToken token;
            if (freshToken == null) {
                // 构造长期令牌
                token = new RefreshToken(
                        null,
                        user.getId(),
                        UUID.randomUUID().toString(),
                        LocalDateTime.now().plusDays(defaultProperties.getRefreshExpire()),
                        null,
                        null
                );
                userRepository.saveToken(token);
            }
            token = freshToken;
            user.login(token);
            return;
        }
        throw new BusinessException(BusinessMessage.USERNAME_OR_PASSWORD_ERROR);
    }

    private static class VIRTUAL_HASH {
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
