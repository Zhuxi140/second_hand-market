package com.zhuxi.user.module.domain.user.service;


import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.interfaces.dto.user.*;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;

/**
 * @author zhuxi
 */
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

    // 续期短时JWT令牌
    RefreshToken renewJwt(RefreshDTO refresh);
}
