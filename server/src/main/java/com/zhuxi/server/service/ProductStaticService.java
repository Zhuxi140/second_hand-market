package com.zhuxi.server.service;

import com.zhuxi.pojo.entity.ProductStatic;

import java.util.List;

public interface ProductStaticService {
    
    /**
     * 插入商品图片
     */
    int insert(ProductStatic productStatic);
    
    /**
     * 根据ID删除商品图片
     */
    int deleteById(Long id);
    
    /**
     * 更新商品图片
     */
    int update(ProductStatic productStatic);
    
    /**
     * 根据ID查询商品图片
     */
    ProductStatic selectById(Long id);
    
    /**
     * 根据商品ID查询商品图片列表
     */
    List<ProductStatic> selectByProductId(Long productId);
    
    /**
     * 根据商品ID和图片类型查询商品图片列表
     */
    List<ProductStatic> selectByProductIdAndType(Long productId, Integer imageType);
    
    /**
     * 查询所有商品图片
     */
    List<ProductStatic> selectAll();
    
    /**
     * 根据条件查询商品图片列表
     */
    List<ProductStatic> selectByCondition(ProductStatic productStatic);
}
