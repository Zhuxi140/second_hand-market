package com.zhuxi.product.module.domain.repository;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;

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
     * @return 商品详情
     */
    ProductDetailVO getShProductDetail(String productId);

    /**
     * 删除商品
     * @param productId 商品id
     */
    void delProduct(Long productId);


    List<MeShProductVO> getMeShProductList(Long userId);

}
