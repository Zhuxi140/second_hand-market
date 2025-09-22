package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.ProductSpec;
import com.zhuxi.server.service.Service.ProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productSpec")
@CrossOrigin(origins = "*")
public class ProductSpecController {
    
    @Autowired
    private ProductSpecService productSpecService;
    
    // 插入商品规格
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody ProductSpec productSpec) {
        int result = productSpecService.insert(productSpec);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除商品规格
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = productSpecService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新商品规格
    @PutMapping("/update")
    public Result<String> update(@RequestBody ProductSpec productSpec) {
        int result = productSpecService.update(productSpec);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询商品规格
    @GetMapping("/get/{id}")
    public Result<ProductSpec> selectById(@PathVariable Long id) {
        ProductSpec productSpec = productSpecService.selectById(id);
        return productSpec != null ? Result.success(productSpec) : Result.fail("商品规格不存在");
    }
    
    // 查询所有商品规格
    @GetMapping("/getAll")
    public Result<List<ProductSpec>> selectAll() {
        List<ProductSpec> productSpecs = productSpecService.selectAll();
        return Result.success(productSpecs);
    }
}

