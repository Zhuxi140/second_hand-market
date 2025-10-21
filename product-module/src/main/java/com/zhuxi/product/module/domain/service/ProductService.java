package com.zhuxi.product.module.domain.service;

import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.product.module.interfaces.vo.CategoryTreeVO;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductService {

    // 商品分类列表
    List<CategoryTreeVO> getCategoryList(int limit, int offset);

    // 发布二手商品
    String publishSh(PublishSHDTO sh, String userSn);

    // 获取二手商品标签
    List<ConditionSHVO> getShConditions();
}
