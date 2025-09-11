package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.ProductSort;
import com.zhuxi.server.helper.ProductSortMapperHelper;
import com.zhuxi.server.service.ProductSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSortServiceImpl implements ProductSortService {
    
    @Autowired
    private ProductSortMapperHelper productSortMapperHelper;
    
    /**
     * 插入商品分类
     */
    @Override
    public int insert(ProductSort productSort) {
        return productSortMapperHelper.insert(productSort);
    }
    
    /**
     * 根据ID删除商品分类
     */
    @Override
    public int deleteById(Long id) {
        return productSortMapperHelper.deleteById(id);
    }
    
    /**
     * 更新商品分类
     */
    @Override
    public int update(ProductSort productSort) {
        return productSortMapperHelper.update(productSort);
    }
    
    /**
     * 根据ID查询商品分类
     */
    @Override
    public ProductSort selectById(Long id) {
        return productSortMapperHelper.selectById(id);
    }
    
    /**
     * 根据父ID查询商品分类列表
     */
    @Override
    public List<ProductSort> selectByParentId(Long parentId) {
        return productSortMapperHelper.selectByParentId(parentId);
    }
    
    /**
     * 查询所有商品分类
     */
    @Override
    public List<ProductSort> selectAll() {
        return productSortMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询商品分类列表
     */
    @Override
    public List<ProductSort> selectByCondition(ProductSort productSort) {
        return productSortMapperHelper.selectByCondition(productSort);
    }
}
