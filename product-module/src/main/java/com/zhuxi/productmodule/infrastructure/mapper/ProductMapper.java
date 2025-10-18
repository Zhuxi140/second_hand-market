package com.zhuxi.productmodule.infrastructure.mapper;

import com.zhuxi.productmodule.domain.model.Product;
import com.zhuxi.productmodule.interfaces.vo.CategoryVO;
import com.zhuxi.productmodule.interfaces.vo.ConditionSHVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhuxi
 */
@Mapper
public interface ProductMapper {

    @Select("SELECT id,name,parent_id,icon_url FROM product_sort LIMIT #{limit} OFFSET #{offset}")
    List<CategoryVO> getCategoryList(int limit,int offset);

    int insert(Product product);

    int update(Product product);

    @Select("SELECT id,name FROM product_condition WHERE type != 0")
    List<ConditionSHVO> getShConditions();

}
