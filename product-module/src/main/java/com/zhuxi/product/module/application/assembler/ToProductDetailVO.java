package com.zhuxi.product.module.application.assembler;

import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;
import com.zhuxi.product.module.interfaces.vo.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.util.List;


/**
 * @author zhuxi
 */
@Mapper
public interface ToProductDetailVO {

    ToProductDetailVO  COVERT = Mappers.getMapper(ToProductDetailVO.class);

    @Mapping(source = "product.productSn.sn", target = "productSn")
    @Mapping(source = "userSn", target = "sellerSn")
    @Mapping(source = "sellerInfo[0]", target = "sellerName")
    @Mapping(source = "sellerInfo[1]", target = "sellerAvatar")
    @Mapping(source = "product.title.title", target = "title")
    @Mapping(source = "product.price.price", target = "price")
    @Mapping(source = "product.conditionId", target = "conditionId")
    @Mapping(source = "product.conditionName", target = "conditionName")
    @Mapping(source = "product.categoryId", target = "categoryId")
    @Mapping(source = "product.categoryName", target = "categoryName")
    @Mapping(source = "product.status.code", target = "status")
    @Mapping(source = "product.location", target = "location")
    @Mapping(source = "product.description", target = "description")
    @Mapping(source = "product.viewCount", target = "viewCount")
    @Mapping(source = "product.hostScore", target = "hostScore")
    @Mapping(source = "productStatics", target = "productImages",qualifiedByName = "toProductImages")
    ProductDetailVO toProductDetailVO(Product product, List<Object> sellerInfo,
                                      List<ProductStatic> productStatics,String userSn);

    @Named("toProductImages")
    default List<ProductImage> toProductImages(List<ProductStatic> productStatics)
    {
        return productStatics.stream()
                .map(s -> {
                    ProductImage image = new ProductImage();
                    image.setSkuId(s.getSkuId());
                    image.setImageUrl(s.getImageUrl());
                    image.setType(s.getImageType().getCode());
                    return image;
                }).toList();
    }
}
