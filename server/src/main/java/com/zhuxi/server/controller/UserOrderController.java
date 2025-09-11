package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.UserOrder;
import com.zhuxi.server.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userOrder")
@CrossOrigin(origins = "*")
public class UserOrderController {
    
    @Autowired
    private UserOrderService userOrderService;
    
    // 插入订单
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody UserOrder userOrder) {
        int result = userOrderService.insert(userOrder);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除订单
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = userOrderService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新订单
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserOrder userOrder) {
        int result = userOrderService.update(userOrder);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询订单
    @GetMapping("/get/{id}")
    public Result<UserOrder> selectById(@PathVariable Long id) {
        UserOrder userOrder = userOrderService.selectById(id);
        return userOrder != null ? Result.success(userOrder) : Result.fail("订单不存在");
    }
    
    // 查询所有订单
    @GetMapping("/getAll")
    public Result<List<UserOrder>> selectAll() {
        List<UserOrder> userOrders = userOrderService.selectAll();
        return Result.success(userOrders);
    }
}
