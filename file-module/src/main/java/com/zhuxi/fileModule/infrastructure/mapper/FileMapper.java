package com.zhuxi.fileModule.infrastructure.mapper;

import com.zhuxi.fileModule.domain.model.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zhuxi
 */
@Mapper
public interface FileMapper {

    @Select("""
    INSERT INTO
    product_static(id, product_id, sku_id, image_url, image_type, sort_order)
    VALUE
    (#{id}, #{productId}, #{skuId}, #{imageUrl}, #{imageType}, #{sortOrder})
    """)
    int save(File file);

    int update(File file);

    @Update("UPDATE user SET avatar = #{url} WHERE userSn = #{sn}")
    int updateAvatar(String url,String sn);

    @Update("UPDATE product_sort SET icon_url = #{url} WHERE id = #{id}")
    int updateIcon(String url,int id);

    @Select("SELECT id sku_id FROM product_spec WHERE sku_sn = #{sn}")
    Long findSkuIdBySn(String sn);

    @Select("SELECT id product_id FROM product WHERE product_sn = #{sn}")
    Long findProductIdBySn(String sn);

    @Select("SELECT sort_order FROM product_static WHERE product_id = #{pId}")
    File findOrderSortBypId(Long pId);

    @Select("SELECT sort_order FROM product_static WHERE sku_id = #{skuId}")
    File findOrderSortBySkuId(Long skuId);

}
