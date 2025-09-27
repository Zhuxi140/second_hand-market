package com.zhuxi.userModule.application.service;


import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.common.utils.BCryptUtils;
import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.domain.user.repository.UserRepository;
import com.zhuxi.userModule.domain.user.service.UserService;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public User login(UserLoginDTO login) {

        User user = repository.getLoginByUsername(login.getUsername());
        if (user.getStatus() == 0){
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        boolean outCome = user.validateLogin(bCryptUtils,user,login,RANDOM);
        if (outCome){
            return user;
        }
        return null;
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

        User user = repository.getISBySn(userSn);
        if (user.getStatus() == 0){
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
        User user = repository.getISBySn(userSn);
        if (user.getStatus() == 0){
            throw new BusinessException(BusinessMessage.USER_IS_LOCK);
        }

        // 比对用户密码
        String newHash = user.changePsWd(bCryptUtils, user, updatePw);

        //写入
        repository.updatePw(user.getId(),newHash);
    }



}
