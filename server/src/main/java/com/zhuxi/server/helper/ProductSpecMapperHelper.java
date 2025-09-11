package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.ProductSpec;
import com.zhuxi.server.mapper.ProductSpecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSpecMapperHelper {
    
    @Autowired
    private ProductSpecMapper productSpecMapper;
    
    /**
     * 插入商品规格
     */
    public int insert(ProductSpec productSpec) {
        return productSpecMapper.insert(productSpec);
    }
    
    /**
     * 根据ID删除商品规格
     */
    public int deleteById(Long id) {
        return productSpecMapper.deleteById(id);
    }
    
    /**
     * 更新商品规格
     */
    public int update(ProductSpec productSpec) {
        return productSpecMapper.update(productSpec);
    }
    
    /**
     * 根据ID查询商品规格
     */
    public ProductSpec selectById(Long id) {
        return productSpecMapper.selectById(id);
    }
    
    /**
     * 根据商品ID查询商品规格列表
     */
    public List<ProductSpec> selectByProductId(Long productId) {
        return productSpecMapper.selectByProductId(productId);
    }
    
    /**
     * 根据SKU编号查询商品规格
     */
    public ProductSpec selectBySkuSn(String skuSn) {
        return productSpecMapper.selectBySkuSn(skuSn);
    }
    
    /**
     * 查询所有商品规格
     */
    public List<ProductSpec> selectAll() {
        return productSpecMapper.selectAll();
    }
    
    /**
     * 根据条件查询商品规格列表
     */
    public List<ProductSpec> selectByCondition(ProductSpec productSpec) {
        return productSpecMapper.selectByCondition(productSpec);
    }
}
