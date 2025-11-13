package com.zhuxi.user.module.interfaces.controller;


import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.interfaces.result.Result;
import com.zhuxi.user.module.domain.address.service.UserAddressService;
import com.zhuxi.user.module.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.user.module.interfaces.dto.address.AdsUpdate;
import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户地址管理控制器
 * @author zhuxi
 */
@Tag(name = "用户地址管理", description = "用户收货地址的增删改查相关接口")
@RestController
@RequestMapping("/user/me")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j(topic = "UserAddressController")
public class UserAddressController {
    

    private final UserAddressService userAddressService;
    
    @Operation(summary = "添加收货地址", description = "为用户添加新的收货地址")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功", 
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "添加失败，可能原因：地址数量已达上限、数据异常等")
    })
    @PostMapping("/{userSn}/addresses")
    @PermissionCheck(Role = Role.user,permission = "user:addAddress",enableDataOwnership = true)
    public Result<String> insert(
            @Parameter(description = "地址信息", required = true)
            @RequestBody @Valid AdsRegisterDTO address,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn
                                 ) {
        String addressSn = userAddressService.insertAddress(address, userSn);
        if (address == null){
            return Result.fail(BusinessMessage.ADD_ADDRESS_ERROR);
        }
        return Result.success(addressSn);
    }
    
    @Operation(summary = "删除收货地址", description = "删除指定的收货地址")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "删除失败，可能原因：地址不存在、数据异常等")
    })
    @DeleteMapping("/{userSn}/addresses/{addressSn}")
    @PermissionCheck(Role = Role.user,permission = "user:delAddress", enableDataOwnership = true)
    public Result<String> deleteById(
            @Parameter(description = "地址编号", required = true)
            @PathVariable String addressSn,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn) {
        userAddressService.deleteBySn(addressSn,userSn);
        return Result.success();
    }

    @Operation(summary = "更新收货地址", description = "更新指定收货地址的信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "更新失败，可能原因：地址不存在、修改地址失败等")
    })
    @PutMapping("/{userSn}/addresses/{addressSn}")
    @PermissionCheck(Role = Role.user,permission = "user:updateAddress", enableDataOwnership = true)
    public Result<String> update(
            @Parameter(description = "地址更新信息", required = true)
            @RequestBody AdsUpdate userAddress,
            @Parameter(description = "用户编号", required = true)
            @PathVariable String userSn,
            @Parameter(description = "地址编号", required = true)
            @PathVariable String addressSn
                                 ) {
        userAddressService.updateAds(userAddress,addressSn,userSn);
        return Result.success();
    }
    

    @Operation(summary = "获取用户地址列表", description = "获取指定用户的所有收货地址")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功", 
                    content = @Content(schema = @Schema(implementation = UserAddressVO.class))),
            @ApiResponse(responseCode = "500", description = "获取失败，可能原因：数据异常等")
    })
    @GetMapping("/{userSn}/addresses")
    @PermissionCheck(Role = Role.user,permission = "user:getAddressList", enableDataOwnership = true)
    public Result<List<UserAddressVO>> getListAddress(@PathVariable String userSn) {
        List<UserAddressVO> list = userAddressService.getListAddress(userSn);
        if (list != null){
            return Result.success("success",list);
        }
        return Result.fail(BusinessMessage.USER_DATA_ERROR);
    }
    

}

