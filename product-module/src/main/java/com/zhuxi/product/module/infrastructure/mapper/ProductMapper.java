package com.zhuxi.product.module.infrastructure.mapper;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhuxi
 */
@Mapper
public interface ProductMapper {

    @Select("SELECT id FROM product WHERE product_sn = #{productSn}")
    Long getProductIdBySn(String productSn);

    @Select("SELECT id,name,parent_id,icon_url FROM product_sort LIMIT #{limit} OFFSET #{offset}")
    List<CategoryVO> getCategoryList(int limit,int offset);

    int insert(Product product);

    int update(Product product);

    @Select("SELECT id,name FROM product_condition WHERE type != 0")
    List<ConditionSHVO> getShConditions();

    List<ShProductVO> getShProductListDesc(ShProductParam param);

    List<ShProductVO> getShProductListAsc(ShProductParam param);

    ProductDetailVO getProductDetail(String productId);

    @Select("""
    SELECT
    sku_id,
    image_url,
    image_type
    FROM product_static ps JOIN product p ON ps.product_id = p.id
    WHERE p.id = #{productId}
    """)
    List<ProductImages> getProductImages(String productId);

}
