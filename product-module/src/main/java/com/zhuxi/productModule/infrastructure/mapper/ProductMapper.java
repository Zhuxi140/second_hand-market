package com.zhuxi.productModule.infrastructure.mapper;

import com.zhuxi.productModule.domain.model.Product;
import com.zhuxi.productModule.interfaces.vo.CategoryVO;
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

}
