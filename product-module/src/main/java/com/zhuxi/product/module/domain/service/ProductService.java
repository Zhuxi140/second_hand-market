package com.zhuxi.product.module.domain.service;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.CategoryTreeVO;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;
import com.zhuxi.product.module.interfaces.vo.ShProductVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductService {

    /**
     * 获取商品分类列表
     * @param limit 显示数量
     * @param offset 偏移量
     * @return 商品分类列表
     */
    List<CategoryTreeVO> getCategoryList(int limit, int offset);

    /**
     * 发布二手商品
     * @param sh 二手商品信息
     * @param userSn 用户编号
     * @return 商品编号
     */
    String publishSh(PublishSHDTO sh, String userSn);

    /**
     * 获取二手商品成色列表
     * @return 二手商品成色列表
     */
    List<ConditionSHVO> getShConditions();

    /**
     * 获取二手商品列表
     * @param shProductParam 二手商品查询参数
     * @return 二手商品列表
     */
    List<ShProductVO> getShProductList(ShProductParam shProductParam);

    ProductDetailVO getProductDetail(String productId);
}
