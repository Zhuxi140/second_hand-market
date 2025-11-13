package com.zhuxi.user.module.domain.address.service;

import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface UserAddressCacheService {

    /**
     * 缓存用户地址信息
     * @param userSn 用户编号
     * @param list 用户地址列表
     */
    void saveInfo(String userSn, List<UserAddressVO> list);

    /**
     * 获取用户地址信息列表缓存
     * @param userSn 用户编号
     * @return 地址列表
     */
    List<UserAddressVO> getInfo(String userSn);
}
