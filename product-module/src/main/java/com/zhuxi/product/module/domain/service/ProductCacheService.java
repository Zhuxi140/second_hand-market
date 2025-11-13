package com.zhuxi.product.module.domain.service;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.interfaces.vo.CategoryVO;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;

import java.util.List;

/**
 * @author zhuxi
 */

public interface ProductCacheService {

    /**
     * 缓存分类目录
     * @param list 分类目录
     */
    void saveCategoryList(List<CategoryVO> list);

    /**
     * 获取分类目录
     * @return 分类目录
     */
    List<CategoryVO> getCategoryList();

    /**
     * 缓存商品
     * @param product 商品
     * @param productSn 商品编号
     */
    void saveShProduct(Product product,String productSn);

    /**
     * 获取商品
     * @param productSn 商品编号
     * @return 商品
     */
    ProductDetailVO getShProduct(String productSn);
}
