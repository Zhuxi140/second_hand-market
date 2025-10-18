package com.zhuxi.usermodule.domain.address.service;



import com.zhuxi.usermodule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.usermodule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.usermodule.interfaces.vo.address.UserAddressVO;

import java.util.List;

public interface UserAddressService {
    // 插入地址
    String insertAddress(AdsRegisterDTO address, String userSn);
    
    // 删除地址
    void deleteBySn(String addressSn,String userSn);

    // 更新地址
    void updateAds(AdsUpdate update, String addressSn, String userSn);

    // 获取用户地址列表
    List<UserAddressVO> getListAddress(String userSn);

}
