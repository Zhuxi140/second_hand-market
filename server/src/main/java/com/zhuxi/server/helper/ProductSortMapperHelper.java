package com.zhuxi.server.helper;

import com.zhuxi.pojo.entity.ProductSort;
import com.zhuxi.server.mapper.ProductSortMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSortMapperHelper {
    
    @Autowired
    private ProductSortMapper productSortMapper;
    
    /**
     * 插入商品分类
     */
    public int insert(ProductSort productSort) {
        return productSortMapper.insert(productSort);
    }
    
    /**
     * 根据ID删除商品分类
     */
    public int deleteById(Long id) {
        return productSortMapper.deleteById(id);
    }
    
    /**
     * 更新商品分类
     */
    public int update(ProductSort productSort) {
        return productSortMapper.update(productSort);
    }
    
    /**
     * 根据ID查询商品分类
     */
    public ProductSort selectById(Long id) {
        return productSortMapper.selectById(id);
    }
    
    /**
     * 根据父ID查询商品分类列表
     */
    public List<ProductSort> selectByParentId(Long parentId) {
        return productSortMapper.selectByParentId(parentId);
    }
    
    /**
     * 查询所有商品分类
     */
    public List<ProductSort> selectAll() {
        return productSortMapper.selectAll();
    }
    
    /**
     * 根据条件查询商品分类列表
     */
    public List<ProductSort> selectByCondition(ProductSort productSort) {
        return productSortMapper.selectByCondition(productSort);
    }
}
