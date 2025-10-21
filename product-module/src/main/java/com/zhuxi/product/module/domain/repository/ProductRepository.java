package com.zhuxi.product.module.domain.repository;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.interfaces.vo.CategoryVO;
import com.zhuxi.product.module.interfaces.vo.ConditionSHVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductRepository {

    List<CategoryVO> getCategoryList(int limit,int offset);

    void save(Product product);

    List<ConditionSHVO> getShConditions();



}
