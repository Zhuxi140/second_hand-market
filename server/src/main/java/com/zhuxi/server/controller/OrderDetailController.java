package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.OrderDetail;
import com.zhuxi.server.service.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetail")
@CrossOrigin(origins = "*")
public class OrderDetailController {
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    // 插入订单明细
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody OrderDetail orderDetail) {
        int result = orderDetailService.insert(orderDetail);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除订单明细
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = orderDetailService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新订单明细
    @PutMapping("/update")
    public Result<String> update(@RequestBody OrderDetail orderDetail) {
        int result = orderDetailService.update(orderDetail);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询订单明细
    @GetMapping("/get/{id}")
    public Result<OrderDetail> selectById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.selectById(id);
        return orderDetail != null ? Result.success(orderDetail) : Result.fail("订单明细不存在");
    }
    
    // 查询所有订单明细
    @GetMapping("/getAll")
    public Result<List<OrderDetail>> selectAll() {
        List<OrderDetail> orderDetails = orderDetailService.selectAll();
        return Result.success(orderDetails);
    }
}

