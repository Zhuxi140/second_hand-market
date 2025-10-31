package com.zhuxi.user.module.application.service.Process;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.utils.BCryptUtils;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.interfaces.dto.user.UserUpdatePwDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
public class ChangePasswordProcess {

    private final UserRepository userRepository;

    /**
     * 检查用户状态
     * @param userSn 用户编号
     * @return 用户领域对象(包含id、状态、密码）
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User checkUserStatus(String userSn){
        // 检查用户状态
        User user = userRepository.getUserIdStatusPasswordBySn(userSn);
        user.checkUserStatus();
        return user;
    }

    /**
     * 比对用户密码
     * @param bCryptUtils 加密工具
     * @param updatePw 修改密码数据
     * @param user 用户领域对象
     */
    public void validatePassword(BCryptUtils bCryptUtils, UserUpdatePwDTO updatePw, User user){
        // 比对用户密码
        boolean result = bCryptUtils.checkPw(updatePw.getOldPassword(), user.getPassword());
        if (!result) {
            throw new BusinessException(BusinessMessage.USER_OLD_PASSWORD_ERROR);
        }
    }

    /**
     * 修改密码
     * @param updatePw 修改密码数据
     * @param user  用户领域对象
     * @param bCryptUtils 加密工具
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void updatePassword(UserUpdatePwDTO updatePw, User user, BCryptUtils bCryptUtils){
        String hashPassword = bCryptUtils.hashCode(updatePw.getNewPassword());
        userRepository.updatePw(user.getId(), hashPassword);
    }
}
