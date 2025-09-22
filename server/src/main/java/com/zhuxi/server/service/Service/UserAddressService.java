package com.zhuxi.server.service.Service;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsRegisterDTO;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsUpdate;
import com.zhuxi.pojo.VO.UserAddress.UserAddressVO;

import java.util.List;

public interface UserAddressService {
    // 插入地址
    Result<String> insertAddress(UserAdsRegisterDTO address, String userSn);
    
    // 删除地址
    Result<String> deleteBySn(String addressSn,String userSn);

    // 更新地址
    Result<String> updateAds(UserAdsUpdate update, String addressSn, String userSn);

    // 获取用户地址列表
    Result<List<UserAddressVO>> getListAddress(String userSn);

}
