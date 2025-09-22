package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.ProductStatic;
import com.zhuxi.server.service.Service.ProductStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productStatic")
@CrossOrigin(origins = "*")
public class ProductStaticController {
    
    @Autowired
    private ProductStaticService productStaticService;
    
    // 插入商品图片
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody ProductStatic productStatic) {
        int result = productStaticService.insert(productStatic);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除商品图片
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = productStaticService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新商品图片
    @PutMapping("/update")
    public Result<String> update(@RequestBody ProductStatic productStatic) {
        int result = productStaticService.update(productStatic);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询商品图片
    @GetMapping("/get/{id}")
    public Result<ProductStatic> selectById(@PathVariable Long id) {
        ProductStatic productStatic = productStaticService.selectById(id);
        return productStatic != null ? Result.success(productStatic) : Result.fail("商品图片不存在");
    }
    
    // 查询所有商品图片
    @GetMapping("/getAll")
    public Result<List<ProductStatic>> selectAll() {
        List<ProductStatic> productStatics = productStaticService.selectAll();
        return Result.success(productStatics);
    }
}

