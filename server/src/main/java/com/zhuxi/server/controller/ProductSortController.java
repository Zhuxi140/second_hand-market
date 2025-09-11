package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.ProductSort;
import com.zhuxi.server.service.ProductSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productSort")
@CrossOrigin(origins = "*")
public class ProductSortController {
    
    @Autowired
    private ProductSortService productSortService;
    
    // 插入商品分类
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody ProductSort productSort) {
        int result = productSortService.insert(productSort);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除商品分类
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = productSortService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新商品分类
    @PutMapping("/update")
    public Result<String> update(@RequestBody ProductSort productSort) {
        int result = productSortService.update(productSort);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询商品分类
    @GetMapping("/get/{id}")
    public Result<ProductSort> selectById(@PathVariable Long id) {
        ProductSort productSort = productSortService.selectById(id);
        return productSort != null ? Result.success(productSort) : Result.fail("商品分类不存在");
    }
    
    // 查询所有商品分类
    @GetMapping("/getAll")
    public Result<List<ProductSort>> selectAll() {
        List<ProductSort> productSorts = productSortService.selectAll();
        return Result.success(productSorts);
    }
}
