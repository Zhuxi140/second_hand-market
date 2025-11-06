package com.zhuxi.user.module.domain.user.service;

import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.user.module.domain.user.model.User;

import java.util.List;

/**
 * @author zhuxi
 */

public interface UserCacheService {

    /**
     * 保存黑名单
     *
     * @param token  token
     * @param role   角色
     * @param expire 过期时间
     */
    void saveBlockList(String token, Role role,long expire);

    /**
     * 保存用户信息
     * (默认30分钟)
     * @param userSn      用户编号
     * @param user        用户信息
     */
    void saveUserInfo(String userSn, User user);

    /**
     * 保存用户部分信息
     * @param userSn      用户编号
     * @param user        用户信息
     * @param keys        指定要保存的部分信息字段名
     */
    void saveUserPartInfo(String userSn, User user,List<String> keys);

    /**
     * 保存用户部分信息
     * @param userSn       用户编号
     * @param values       指定要保存的部分信息字段名及其值
     */
    void saveUserPartInfo(String userSn, List<Object> values);

    /**
     * 获取用户缓存某一key信息
     * @param userSn      用户编号
     * @param key         指定要获取的信息字段名
     */
    Object getOneInfo(String userSn,String key);

    /**
     * 删除用户信息
     * @param userSn 用户编号
     */
    void deleteUserInfo(String userSn);

    /**
     *  获取指定的用户信息
     * @param userSn 用户编号
     * @param keys 指定要获取的信息字段名  为null时获取所有字段
     * @return 用户信息
     */
    User getUserInfo(String userSn, List<String> keys);
}
