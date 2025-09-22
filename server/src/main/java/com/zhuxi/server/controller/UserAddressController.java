package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsRegisterDTO;
import com.zhuxi.pojo.DTO.UserAddress.UserAdsUpdate;
import com.zhuxi.pojo.VO.UserAddress.UserAddressVO;
import com.zhuxi.server.service.Service.UserAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/me")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserAddressController {
    

    private final UserAddressService userAddressService;
    
    // 插入地址
    @PostMapping("/{userSn}/addresses")
    public Result<String> insert(@RequestBody @Valid UserAdsRegisterDTO address,
                                 @PathVariable String userSn
                                 ) {
        return userAddressService.insertAddress(address,userSn);

    }
    
    // 删除地址
    @DeleteMapping("/{userSn}/addresses/{addressSn}")
    public Result<String> deleteById(@PathVariable String addressSn,@PathVariable String userSn) {
        return userAddressService.deleteBySn(addressSn,userSn);
    }

    // 更新地址
    @PutMapping("/{userSn}/addresses/{addressSn}")
    public Result<String> update(@RequestBody UserAdsUpdate userAddress,
                                 @PathVariable String userSn,
                                 @PathVariable String addressSn
                                 ) {
        return userAddressService.updateAds(userAddress,addressSn,userSn);
    }
    

    @GetMapping("/{userSn}/addresses")
    public Result<List<UserAddressVO>> getListAddress(@PathVariable String userSn) {
        return userAddressService.getListAddress(userSn);
    }
    

}

