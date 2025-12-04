package com.zhuxi.product.module.domain.repository;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.objectValue.HostScore;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductRepository {

    /**
     * 获取商品分类列表
     * @param limit 限制
     * @param offset 偏移
     * @return 分类列表
     */
    List<CategoryVO> getCategoryList(int limit,int offset);

    /**
     * 保存商品
     * @param product 商品
     */
    void save(Product product);

    /**
     * 根据商品编号获取商品id
     * @param productSn 商品编号
     * @return 商品id
     */
    Long getProductIdBySn(String productSn);

    /**
     * 根据用户编号获取用户id
     * @param userSn 用户编号
     * @return 用户id
     */
    Long getUserIdBySn(String userSn);

    /**
     * 获取二手商品成色列表
     * @return 二手商品成色列表
     */
    List<ConditionSHVO> getShConditions();

    /**
     * 获取二手商品列表(降序)
     * @param shProductParam 参数
     * @return 商品列表
     */
    List<ShProductVO> getShProductListDesc(ShProductParam shProductParam);

    /**
     * 获取二手商品列表(升序)
     * @param shProductParam 参数
     * @return 升序商品列表
     */
    List<ShProductVO> getShProductListAsc(ShProductParam shProductParam);


    /**
     * 获取商品详情
     * @param productId 商品id
     * @param isGetStatic 是否获取静态信息
     * @return 商品详情
     */
    ProductDetailVO getShProductDetail(String productId,boolean isGetStatic);

    /**
     * 删除商品
     * @param productId 商品id
     */
    void delProduct(Long productId);

    /**
     * 获取我的二手商品列表
     * @param userId 用户id
     * @return 我的二手商品列表
     */
    List<MeShProductVO> getMeShProductList(Long userId);


    /**
     * 根据成色id获取成色名称
     * @param conditionId 成色id
     * @return 成色名称
     */
    String gerConditionNameById(Integer conditionId);

    /**
     * 根据分类id获取分类名称
     * @param categoryId 分类id
     * @return 分类名称
     */
    String getCategoryNameById(Long categoryId);


    /**
     * 获取商品静态信息
     * @param productId 商品id
     * @return 商品静态信息列表
     */
    List<ProductStatic> getProductStatics(Long productId);

    /**
     * 获取卖家信息(昵称、头像)
     * @param userSn 用户编号
     * @return 卖家信息
     */
    List<Object> getSellerInfo(String userSn);

   /**
     * 获取商品信息(用于缓存)
     * @param productId 商品id
     * @return 商品信息
     */
    Product getProductForCache(Long productId);

    /**
     * 根据用户id获取用户编号
     * @param userId 用户id
     * @return 用户编号
     */
    String getUserSn(Long userId);


    /**
     * 获取商品热度指数
     * @param productId 商品id
     * @return 热度指数
     */
    HostScore getHostScore(Long productId);


}
