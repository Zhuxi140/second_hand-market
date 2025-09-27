package com.zhuxi.userModule.domain.address.repository;

import com.zhuxi.userModule.domain.address.model.UserAddress;
import com.zhuxi.userModule.interfaces.dto.address.AdsBaseUpdate;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;

import java.util.List;

public interface UserAddressRepository {

    UserAddress getUserIdBySn(String userSn);

    void checkAddressCount(Long userId);

    void save(UserAddress address);

    UserAddress getAddressIdBySn(String addressSn);

    Long getDefaultId(Long userId);

    void cancelDefault(Long addressId);

    void deleteAddress(String addressSn);

    List<UserAddressVO> gerListAddress(Long userId);


}
