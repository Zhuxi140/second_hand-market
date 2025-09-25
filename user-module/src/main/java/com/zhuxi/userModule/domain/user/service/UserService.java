package com.zhuxi.userModule.domain.user.service;


import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;

public interface UserService {
    // 注册用户
    Result<UserRegisterVO> register(UserRegisterDTO user);

    // 登录
    Result<UserLoginVO> login(UserLoginDTO login);
    
    // 登出
    Result<String> logout();

    // 更新用户
    Result<String> updateInfo(UserUpdateInfoDTO user, String userSn);
    
    // 获取用户信息
    Result<UserViewVO> getUserInfo(String userSn);

    // 修改密码
    Result<String> updatePassword(UserUpdatePwDTO updatePw, String userSn);
}
