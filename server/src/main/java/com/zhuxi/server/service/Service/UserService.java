package com.zhuxi.server.service.Service;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.DTO.User.UserLoginDTO;
import com.zhuxi.pojo.DTO.User.UserRegisterDTO;
import com.zhuxi.pojo.DTO.User.UserUpdateInfoDTO;
import com.zhuxi.pojo.DTO.User.UserUpdatePwDTO;
import com.zhuxi.pojo.VO.User.UserLoginVO;
import com.zhuxi.pojo.VO.User.UserRegisterVO;
import com.zhuxi.pojo.VO.User.UserViewVO;

public interface UserService {
    // 注册用户
    Result<UserRegisterVO> register(UserRegisterDTO user);

    // 登录
    Result<UserLoginVO> login(UserLoginDTO login);
    
    // 登出
    Result<String> logout();

    // 更新用户
    Result<String> updateInfo(UserUpdateInfoDTO user,String userSn);
    
    // 获取用户信息
    Result<UserViewVO> getUserInfo(String userSn);

    // 修改密码
    Result<String> updatePassword(UserUpdatePwDTO  updatePw, String userSn);
}
