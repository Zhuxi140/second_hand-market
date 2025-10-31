package com.zhuxi.user.module.domain.user.repository;

import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.model.RefreshToken;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;

/**
 * @author zhuxi
 */
public interface UserRepository {

    /**
     * 保存用户
     * @param user  用户领域对象
     */
    void save(User user);

    /**
     * 保存用户角色
     * @param userId 用户id
     * @param roleId 角色id
     */
    void saveRole(Long userId, int roleId);

    /**
     * 检查角色是否存在
     * @param roleId 角色id
     */
    void checkRoleExist(int roleId);

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色
     */
    Role getRole(Long userId);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 存在返回1，不存在返回0
     */
    int checkUsernameExist(String  username);

    /**
     * 获取用户登录信息
     * @param username 用户名
     * @return 用户登录信息
     */
    User getLoginByUsername(String username);

    /**
     * 修改密码
     * @param id 用户id
     * @param newPw 新密码
     */
    void updatePw(Long id, String newPw);

    /**
     * 获取用户信息
     * @param userSn 用户sn
     * @return 用户信息VO
     */
    UserViewVO getUserInfo(String userSn);

    /**
     * 获取用户id、角色、状态
     * @param userSn 用户sn
     * @return 用户领域对象
     */
    User getUserIdStatusBySn(String userSn);

    /**
     * 获取用户id、状态、密码
     * @param userSn 用户sn
     * @return 用户领域对象
     */
    User getUserIdStatusPasswordBySn(String userSn);

    /**
     * 获取用户id
     * @param userSn 用户sn
     * @return 用户id
     */
    Long getUserId(String userSn);

    /**
     * 保存刷新令牌
     * @param refreshToken 刷新令牌
     */
    void saveToken(RefreshToken refreshToken);

    /**
     * 根据用户id获取刷新令牌信息(令牌hash、过期时间)
     * @param userId 用户id
     * @return 刷新令牌信息
     */
    RefreshToken getTokenByUserId(Long userId);

    /**
     * 根据userId检查用户的刷新令牌是否存在
     * @param userId 用户id
     * @return 存在返回令牌id 不存在返回null
     */
    Long checkFreshTokenExist(Long userId);

    /**
     * 删除刷新令牌
     * @param tokenId 刷新令牌id
     */
    void deleteToken(Long tokenId);


}
