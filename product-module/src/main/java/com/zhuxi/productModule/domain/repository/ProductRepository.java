package com.zhuxi.productModule.domain.repository;

import com.zhuxi.productModule.domain.model.Product;
import com.zhuxi.productModule.interfaces.vo.CategoryVO;

import java.util.List;

/**
 * @author zhuxi
 */
public interface ProductRepository {


    List<CategoryVO> getCategoryList(int limit,int offset);

    void save(Product product);

}
