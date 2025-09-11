package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.ProductStatic;
import com.zhuxi.server.mapper.ProductStaticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductStaticMapperHelper {
    
    @Autowired
    private ProductStaticMapper productStaticMapper;
    
    /**
     * 插入商品图片
     */
    public int insert(ProductStatic productStatic) {
        return productStaticMapper.insert(productStatic);
    }
    
    /**
     * 根据ID删除商品图片
     */
    public int deleteById(Long id) {
        return productStaticMapper.deleteById(id);
    }
    
    /**
     * 更新商品图片
     */
    public int update(ProductStatic productStatic) {
        return productStaticMapper.update(productStatic);
    }
    
    /**
     * 根据ID查询商品图片
     */
    public ProductStatic selectById(Long id) {
        return productStaticMapper.selectById(id);
    }
    
    /**
     * 根据商品ID查询商品图片列表
     */
    public List<ProductStatic> selectByProductId(Long productId) {
        return productStaticMapper.selectByProductId(productId);
    }
    
    /**
     * 根据商品ID和图片类型查询商品图片列表
     */
    public List<ProductStatic> selectByProductIdAndType(Long productId, Integer imageType) {
        return productStaticMapper.selectByProductIdAndType(productId, imageType);
    }
    
    /**
     * 查询所有商品图片
     */
    public List<ProductStatic> selectAll() {
        return productStaticMapper.selectAll();
    }
    
    /**
     * 根据条件查询商品图片列表
     */
    public List<ProductStatic> selectByCondition(ProductStatic productStatic) {
        return productStaticMapper.selectByCondition(productStatic);
    }
}
