package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.Product;
import com.zhuxi.server.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapperHelper {
    
    @Autowired
    private ProductMapper productMapper;
    
    /**
     * 插入商品
     */
    public int insert(Product product) {
        return productMapper.insert(product);
    }
    
    /**
     * 根据ID删除商品
     */
    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }
    
    /**
     * 更新商品
     */
    public int update(Product product) {
        return productMapper.update(product);
    }
    
    /**
     * 根据ID查询商品
     */
    public Product selectById(Long id) {
        return productMapper.selectById(id);
    }
    
    /**
     * 根据商品编号查询商品
     */
    public Product selectByProductSn(String productSn) {
        return productMapper.selectByProductSn(productSn);
    }
    
    /**
     * 根据卖家ID查询商品列表
     */
    public List<Product> selectBySellerId(Long sellerId) {
        return productMapper.selectBySellerId(sellerId);
    }
    
    /**
     * 根据分类ID查询商品列表
     */
    public List<Product> selectByCategoryId(Long categoryId) {
        return productMapper.selectByCategoryId(categoryId);
    }
    
    /**
     * 查询所有商品
     */
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }
    
    /**
     * 根据条件查询商品列表
     */
    public List<Product> selectByCondition(Product product) {
        return productMapper.selectByCondition(product);
    }
}
