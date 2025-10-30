package com.zhuxi.user.module.domain.user.service;


import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.interfaces.dto.user.*;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;

/**
 * @author zhuxi
 */
public interface UserService {
    /**
     * 用户注册
     * @param register 注册信息
     * @return 用户领域对象
     */
    User register(UserRegisterDTO register);

    /**
     * 用户登录
     * @param login 登录信息
     * @return 用户领域对象
     */
    User login(UserLoginDTO login);

    /**
     * 用户登出
     * @param userSn 用户编号
     * @param token 访问令牌
     */
    void logout(String userSn, String token);

    /**
     * 修改用户信息
     * @param update  修改用户信息
     * @param userSn 用户编号
     */
    void updateInfo(UserUpdateInfoDTO update, String userSn);

    /**
     * 获取用户信息
     * @param userSn 用户编号
     * @return 用户信息VO
     */
    UserViewVO getUserInfo(String userSn);

    /**
     * 修改用户密码
     * @param updatePw 修改密码信息DTO
     * @param userSn 用户编号
     * @param token 访问令牌
     */
    void updatePassword(UserUpdatePwDTO updatePw, String userSn,String token);

    /**
     * 刷新令牌
     * @param refresh 刷新令牌信息DTO
     * @return 新令牌信息
     */
    RefreshToken renewJwt(RefreshDTO refresh);
}
