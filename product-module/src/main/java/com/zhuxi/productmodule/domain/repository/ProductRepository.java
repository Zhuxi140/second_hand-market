package com.zhuxi.productmodule.domain.repository;

import com.zhuxi.productmodule.domain.model.Product;
import com.zhuxi.productmodule.interfaces.vo.CategoryVO;
import com.zhuxi.productmodule.interfaces.vo.ConditionSHVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductRepository {

    List<CategoryVO> getCategoryList(int limit,int offset);

    void save(Product product);

    List<ConditionSHVO> getShConditions();



}
