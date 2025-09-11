package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.Product;
import com.zhuxi.server.helper.ProductMapperHelper;
import com.zhuxi.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductMapperHelper productMapperHelper;
    
    /**
     * 插入商品
     */
    @Override
    public int insert(Product product) {
        return productMapperHelper.insert(product);
    }
    
    /**
     * 根据ID删除商品
     */
    @Override
    public int deleteById(Long id) {
        return productMapperHelper.deleteById(id);
    }
    
    /**
     * 更新商品
     */
    @Override
    public int update(Product product) {
        return productMapperHelper.update(product);
    }
    
    /**
     * 根据ID查询商品
     */
    @Override
    public Product selectById(Long id) {
        return productMapperHelper.selectById(id);
    }
    
    /**
     * 根据商品编号查询商品
     */
    @Override
    public Product selectByProductSn(String productSn) {
        return productMapperHelper.selectByProductSn(productSn);
    }
    
    /**
     * 根据卖家ID查询商品列表
     */
    @Override
    public List<Product> selectBySellerId(Long sellerId) {
        return productMapperHelper.selectBySellerId(sellerId);
    }
    
    /**
     * 查询所有商品
     */
    @Override
    public List<Product> selectAll() {
        return productMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询商品列表
     */
    @Override
    public List<Product> selectByCondition(Product product) {
        return productMapperHelper.selectByCondition(product);
    }
}
