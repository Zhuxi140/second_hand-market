package com.zhuxi.product.module.domain.service;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
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
     * 缓存商品静态数据
     * @param pStatics 商品静态数据
     * @param productSn 商品编号
     */
    void saveProductStatic(List<ProductStatic> pStatics,String productSn);

    /**
     * 获取商品
     * @param productSn 商品编号
     * @return list第一个元素为标识返回的数据是那种类型，第二个元素及以后为缓存数据
     * (全命中返回DetailVO，否则仅返回命中的缓存)
     */
    List<Object> getShProductDetail(String productSn);
}
