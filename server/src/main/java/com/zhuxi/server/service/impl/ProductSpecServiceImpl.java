package com.zhuxi.server.service.impl;

import com.zhuxi.pojo.entity.ProductSpec;
import com.zhuxi.server.helper.ProductSpecMapperHelper;
import com.zhuxi.server.service.ProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    
    @Autowired
    private ProductSpecMapperHelper productSpecMapperHelper;
    
    /**
     * 插入商品规格
     */
    @Override
    public int insert(ProductSpec productSpec) {
        return productSpecMapperHelper.insert(productSpec);
    }
    
    /**
     * 根据ID删除商品规格
     */
    @Override
    public int deleteById(Long id) {
        return productSpecMapperHelper.deleteById(id);
    }
    
    /**
     * 更新商品规格
     */
    @Override
    public int update(ProductSpec productSpec) {
        return productSpecMapperHelper.update(productSpec);
    }
    
    /**
     * 根据ID查询商品规格
     */
    @Override
    public ProductSpec selectById(Long id) {
        return productSpecMapperHelper.selectById(id);
    }
    
    /**
     * 根据商品ID查询商品规格列表
     */
    @Override
    public List<ProductSpec> selectByProductId(Long productId) {
        return productSpecMapperHelper.selectByProductId(productId);
    }
    
    /**
     * 根据SKU编号查询商品规格
     */
    @Override
    public ProductSpec selectBySkuSn(String skuSn) {
        return productSpecMapperHelper.selectBySkuSn(skuSn);
    }
    
    /**
     * 查询所有商品规格
     */
    @Override
    public List<ProductSpec> selectAll() {
        return productSpecMapperHelper.selectAll();
    }
    
    /**
     * 根据条件查询商品规格列表
     */
    @Override
    public List<ProductSpec> selectByCondition(ProductSpec productSpec) {
        return productSpecMapperHelper.selectByCondition(productSpec);
    }
}

