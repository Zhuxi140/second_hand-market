package com.zhuxi.server.service.Service;

import com.zhuxi.pojo.entity.ProductSort;

import java.util.List;

public interface ProductSortService {
    
    /**
     * 插入商品分类
     */
    int insert(ProductSort productSort);
    
    /**
     * 根据ID删除商品分类
     */
    int deleteById(Long id);
    
    /**
     * 更新商品分类
     */
    int update(ProductSort productSort);
    
    /**
     * 根据ID查询商品分类
     */
    ProductSort selectById(Long id);
    
    /**
     * 根据父ID查询商品分类列表
     */
    List<ProductSort> selectByParentId(Long parentId);
    
    /**
     * 查询所有商品分类
     */
    List<ProductSort> selectAll();
    
    /**
     * 根据条件查询商品分类列表
     */
    List<ProductSort> selectByCondition(ProductSort productSort);
}

