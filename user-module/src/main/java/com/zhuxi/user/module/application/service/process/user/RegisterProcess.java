package com.zhuxi.user.module.application.service.process.user;

import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.common.shared.utils.BCryptUtils;
import com.zhuxi.user.module.application.command.RegisterCommand;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.interfaces.dto.user.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
public class RegisterProcess {

    private final UserRepository repository;

    /**
     * 检查用户名是否存在
     * @param register 注册信息
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkNameExist(UserRegisterDTO register) {
        int exist = repository.checkUsernameExist(register.getUsername());
        if (exist == 1) {
            throw new BusinessException(BusinessMessage.USERNAME_IS_EXIST);
        }
    }


    /**
     * 持久化用户数据
     * @param user 用户领域对象
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void persistUserData(User user){
        //写入
        repository.save(user);
        //检测角色是否存在
        repository.checkRoleExist(user.getRole().getId());
        //写入角色
        repository.saveRole(user.getId(), user.getRole().getId());
    }


    /**
     * 设置默认昵称
     * @param register 注册信息
     * @return 默认昵称
     */
    public String setDefaultNickName(UserRegisterDTO register) {
        String nickName;
        if (register.getNickname() == null || register.getNickname().isBlank()) {
            nickName = "换换" + 1011;
        } else {
            nickName = register.getNickname();
        }
        return nickName;
    }

    /**
     * 构建用户
     *
     * @param register 注册信息
     * @param nickName 昵称
     * @param bCryptUtils BCryptUtils
     * @param properties 默认配置
     * @return User
     */
    public User buildUser(UserRegisterDTO register, String nickName,
                           BCryptUtils bCryptUtils, CacheKeyProperties properties){
        // 加密密码
        String hashPassword = bCryptUtils.hashCode(register.getPassword());
        //注册
        User user = new User();
        RegisterCommand registerCommand = new RegisterCommand(register, properties, hashPassword,nickName);
        user.register(registerCommand);
        return user;
    }
}
