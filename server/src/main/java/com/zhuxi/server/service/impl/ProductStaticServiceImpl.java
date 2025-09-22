package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.ProductStatic;
import com.zhuxi.server.helper.ProductStaticMapperHelper;
import com.zhuxi.server.service.Service.ProductStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStaticServiceImpl implements ProductStaticService {
    
    @Autowired
    private ProductStaticMapperHelper productStaticMapperHelper;
    
    /**
     * 插入商品图片
     */
    @Override
    public int insert(ProductStatic productStatic) {
        return productStaticMapperHelper.insert(productStatic);
    }
    
    /**
     * 根据ID删除商品图片
     */
    @Override
    public int deleteById(Long id) {
        return productStaticMapperHelper.deleteById(id);
    }
    
    /**
     * 更新商品图片
     */
    @Override
    public int update(ProductStatic productStatic) {
        return productStaticMapperHelper.update(productStatic);
    }
    
    /**
     * 根据ID查询商品图片
     */
    @Override
    public ProductStatic selectById(Long id) {
        return productStaticMapperHelper.selectById(id);
    }
    
    /**
     * 根据商品ID查询商品图片列表
     */
    @Override
    public List<ProductStatic> selectByProductId(Long productId) {
        return productStaticMapperHelper.selectByProductId(productId);
    }
    
    /**
     * 根据商品ID和图片类型查询商品图片列表
     */
    @Override
    public List<ProductStatic> selectByProductIdAndType(Long productId, Integer imageType) {
        return productStaticMapperHelper.selectByProductIdAndType(productId, imageType);
    }
    
    /**
     * 查询所有商品图片
     */
    @Override
    public List<ProductStatic> selectAll() {
        return productStaticMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询商品图片列表
     */
    @Override
    public List<ProductStatic> selectByCondition(ProductStatic productStatic) {
        return productStaticMapperHelper.selectByCondition(productStatic);
    }
}

