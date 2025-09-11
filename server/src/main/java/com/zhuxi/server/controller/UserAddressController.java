package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.UserAddress;
import com.zhuxi.server.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userAddress")
@CrossOrigin(origins = "*")
public class UserAddressController {
    
    @Autowired
    private UserAddressService userAddressService;
    
    // 插入地址
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody UserAddress userAddress) {
        int result = userAddressService.insert(userAddress);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除地址
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = userAddressService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新地址
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserAddress userAddress) {
        int result = userAddressService.update(userAddress);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询地址
    @GetMapping("/get/{id}")
    public Result<UserAddress> selectById(@PathVariable Long id) {
        UserAddress userAddress = userAddressService.selectById(id);
        return userAddress != null ? Result.success(userAddress) : Result.fail("地址不存在");
    }
    
    // 查询所有地址
    @GetMapping("/getAll")
    public Result<List<UserAddress>> selectAll() {
        List<UserAddress> userAddresses = userAddressService.selectAll();
        return Result.success(userAddresses);
    }
}
