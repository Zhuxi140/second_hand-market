package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.ProductSpec;

import java.util.List;

public interface ProductSpecService {
    
    /**
     * 插入商品规格
     */
    int insert(ProductSpec productSpec);
    
    /**
     * 根据ID删除商品规格
     */
    int deleteById(Long id);
    
    /**
     * 更新商品规格
     */
    int update(ProductSpec productSpec);
    
    /**
     * 根据ID查询商品规格
     */
    ProductSpec selectById(Long id);
    
    /**
     * 根据商品ID查询商品规格列表
     */
    List<ProductSpec> selectByProductId(Long productId);
    
    /**
     * 根据SKU编号查询商品规格
     */
    ProductSpec selectBySkuSn(String skuSn);
    
    /**
     * 查询所有商品规格
     */
    List<ProductSpec> selectAll();
    
    /**
     * 根据条件查询商品规格列表
     */
    List<ProductSpec> selectByCondition(ProductSpec productSpec);
}
