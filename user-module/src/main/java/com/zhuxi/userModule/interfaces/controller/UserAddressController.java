package com.zhuxi.userModule.interfaces.controller;


import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.domain.address.service.UserAddressService;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;
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
    public Result<String> insert(@RequestBody @Valid AdsRegisterDTO address,
                                 @PathVariable String userSn
                                 ) {
        String addressSn = userAddressService.insertAddress(address, userSn);
        if (address == null){
            return Result.fail(BusinessMessage.ADD_ADDRESS_ERROR);
        }
        return Result.success(addressSn);
    }
    
    // 删除地址
    @DeleteMapping("/{userSn}/addresses/{addressSn}")
    public Result<String> deleteById(@PathVariable String addressSn,@PathVariable String userSn) {
        userAddressService.deleteBySn(addressSn,userSn);
        return Result.success();
    }

    // 更新地址
    @PutMapping("/{userSn}/addresses/{addressSn}")
    public Result<String> update(@RequestBody AdsUpdate userAddress,
                                 @PathVariable String userSn,
                                 @PathVariable String addressSn
                                 ) {
        userAddressService.updateAds(userAddress,addressSn,userSn);
        return Result.success();
    }
    

    @GetMapping("/{userSn}/addresses")
    public Result<List<UserAddressVO>> getListAddress(@PathVariable String userSn) {
        List<UserAddressVO> list = userAddressService.getListAddress(userSn);
        if (list != null){
            return Result.success("success",list);
        }
        return Result.fail(BusinessMessage.USER_DATA_ERROR);
    }
    

}

