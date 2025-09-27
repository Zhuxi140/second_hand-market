package com.zhuxi.userModule.domain.user.service;


import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;

public interface UserService {
    // 注册用户
    User register(UserRegisterDTO user);

    // 登录
    User login(UserLoginDTO login);
    
    // 登出
    void logout();

    // 更新用户
    void updateInfo(UserUpdateInfoDTO user, String userSn);
    
    // 获取用户信息
    UserViewVO getUserInfo(String userSn);

    // 修改密码
    void updatePassword(UserUpdatePwDTO updatePw, String userSn);
}
