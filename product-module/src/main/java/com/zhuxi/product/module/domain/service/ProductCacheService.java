package com.zhuxi.product.module.domain.service;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.interfaces.vo.CategoryVO;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;

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
     * 缓存成色列表
     * @param list 成色列表
     */
    void saveConditionList(List<ConditionSHVO> list);

    /**
     * 获取成色列表
     * @return 成色列表
     */
    List<ConditionSHVO> getShConditions();

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
     * 获取商品详情缓存信息中的商品部分信息
     * @param productSn 商品编号
     * @return 所需商品缓存信息
     */
    Product getDetailProductInfo(String productSn);


    /**
     * 获取商品静态数据
     * @param productSn 商品编号
     * @return 商品静态数据
     */
    List<ProductStatic> getProductStatics(String productSn);

    /**
     * 获取用户编号
     * @param userId 用户编号
     * @return 用户编号
     */
    public String getUserSn(Long userId);

    /**
     * 获取商品详细信息中的卖家信息
     * @param userSn 用户编号
     * @return 卖家信息
     */
    List<Object> getSellerInfo(String userSn);
}
