package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.Shop;
import com.zhuxi.server.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "*")
public class ShopController {
    
    @Autowired
    private ShopService shopService;
    
    // 插入店铺
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody Shop shop) {
        int result = shopService.insert(shop);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除店铺
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = shopService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新店铺
    @PutMapping("/update")
    public Result<String> update(@RequestBody Shop shop) {
        int result = shopService.update(shop);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询店铺
    @GetMapping("/get/{id}")
    public Result<Shop> selectById(@PathVariable Long id) {
        Shop shop = shopService.selectById(id);
        return shop != null ? Result.success(shop) : Result.fail("店铺不存在");
    }
    
    // 查询所有店铺
    @GetMapping("/getAll")
    public Result<List<Shop>> selectAll() {
        List<Shop> shops = shopService.selectAll();
        return Result.success(shops);
    }
}
