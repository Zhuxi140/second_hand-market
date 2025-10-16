package com.zhuxi.productModule.domain.service;

import com.zhuxi.productModule.interfaces.dto.PublishSHDTO;
import com.zhuxi.productModule.interfaces.vo.CategoryTreeVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductService {

    // 商品分类列表
    List<CategoryTreeVO> getCategoryList(int limit, int offset);

    // 发布二手商品
    String publishSh(PublishSHDTO sh, String userSn);
}
