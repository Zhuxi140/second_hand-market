package com.zhuxi.server.service.Service;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.DTO.Product.ProductSdDTO;
import com.zhuxi.pojo.entity.Product;

import java.util.List;

public interface ProductService {
    // 发布二手商品
    Result<String> pSdProduct(ProductSdDTO product,String userSn,Integer isDraft);
    
    // 根据ID删除商品
    int deleteById(Long id);
    
    // 更新商品
    int update(Product product);
    
    // 根据ID查询商品
    Product selectById(Long id);
    
    // 根据商品编号查询商品
    Product selectByProductSn(String productSn);
    
    // 根据卖家ID查询商品列表
    List<Product> selectBySellerId(Long sellerId);
    
    // 查询所有商品
    List<Product> selectAll();
    
    // 根据条件查询商品列表
    List<Product> selectByCondition(Product product);
}
