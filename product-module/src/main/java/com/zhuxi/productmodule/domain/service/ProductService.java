package com.zhuxi.productmodule.domain.service;

import com.zhuxi.productmodule.interfaces.dto.PublishSHDTO;
import com.zhuxi.productmodule.interfaces.vo.CategoryTreeVO;
import com.zhuxi.productmodule.interfaces.vo.ConditionSHVO;

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
