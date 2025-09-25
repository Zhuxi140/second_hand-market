package com.zhuxi.userModule.domain.address.service;



import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;

import java.util.List;

public interface UserAddressService {
    // 插入地址
    Result<String> insertAddress(AdsRegisterDTO address, String userSn);
    
    // 删除地址
    Result<String> deleteBySn(String addressSn,String userSn);

    // 更新地址
    Result<String> updateAds(AdsUpdate update, String addressSn, String userSn);

    // 获取用户地址列表
    Result<List<UserAddressVO>> getListAddress(String userSn);

}
