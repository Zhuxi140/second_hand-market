package com.zhuxi.user.module.domain.address.repository;

import com.zhuxi.user.module.domain.address.model.UserAddress;
import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface UserAddressRepository {

    /**
     *  根据用户编号获取用户id
     * @param userSn 用户编号
     * @return 地址领域对象
     */
    UserAddress getUserIdBySn(String userSn);

    /**
     *  检查用户地址数量
     *  地址最大数量: 20
     *  超过20直接抛出异常
     * @param userId 用户id
     */
    void checkAddressCount(Long userId);

    /**
     * 写入
     * @param address 地址领域对象
     */
    void save(UserAddress address);

    /**
     *  根据地址编号获取默认地址id
     * @param addressSn 地址编号
     * @return 领域对象
     */
    UserAddress getAdIdOrDefaultBySn(String addressSn);

    /**
     * 获取用户的默认地址id
     * @param userId  用户id
     * @return 默认地址id
     */
    Long getDefaultId(Long userId);

    /**
     * 取消默认地址
     * @param addressId 地址id
     */
    void cancelDefault(Long addressId);

    /**
     * 删除地址
     * @param addressId 地址id
     */
    void deleteAddress(Long addressId);

    /**
     * 获取用户地址列表
     * @param userId 用户id
     * @return 地址列表VO
     */
    List<UserAddressVO> gerListAddress(Long userId);

    /**
     * 检查地址id是否有效(存在)
     * @param addressSn 地址id
     * @return 存在返回地址id，不存在抛出异常
     */
    Long checkIdEffective(String addressSn);

}
