package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.entity.Product;
import com.zhuxi.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // 插入商品
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody Product product) {
        int result = productService.insert(product);
        return result > 0 ? Result.success("插入成功") : Result.fail("插入失败");
    }
    
    // 根据ID删除商品
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        int result = productService.deleteById(id);
        return result > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }
    
    // 更新商品
    @PutMapping("/update")
    public Result<String> update(@RequestBody Product product) {
        int result = productService.update(product);
        return result > 0 ? Result.success("更新成功") : Result.fail("更新失败");
    }
    
    // 根据ID查询商品
    @GetMapping("/get/{id}")
    public Result<Product> selectById(@PathVariable Long id) {
        Product product = productService.selectById(id);
        return product != null ? Result.success(product) : Result.fail("商品不存在");
    }
    
    // 根据商品编号查询商品
    @GetMapping("/getByProductSn/{productSn}")
    public Result<Product> selectByProductSn(@PathVariable String productSn) {
        Product product = productService.selectByProductSn(productSn);
        return product != null ? Result.success(product) : Result.fail("商品不存在");
    }
    
    // 根据卖家ID查询商品列表
    @GetMapping("/getBySellerId/{sellerId}")
    public Result<List<Product>> selectBySellerId(@PathVariable Long sellerId) {
        List<Product> products = productService.selectBySellerId(sellerId);
        return Result.success(products);
    }
    
    // 查询所有商品
    @GetMapping("/getAll")
    public Result<List<Product>> selectAll() {
        List<Product> products = productService.selectAll();
        return Result.success(products);
    }
}
