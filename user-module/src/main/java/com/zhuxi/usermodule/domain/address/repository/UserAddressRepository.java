package com.zhuxi.usermodule.domain.address.repository;

import com.zhuxi.usermodule.domain.address.model.UserAddress;
import com.zhuxi.usermodule.interfaces.vo.address.UserAddressVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface UserAddressRepository {

    UserAddress getUserIdBySn(String userSn);

    void checkAddressCount(Long userId);

    void save(UserAddress address);

    UserAddress getAdIdOrDefaultBySn(String addressSn);

    Long getDefaultId(Long userId);

    void cancelDefault(Long addressId);

    void deleteAddress(String addressSn);

    List<UserAddressVO> gerListAddress(Long userId);


}
