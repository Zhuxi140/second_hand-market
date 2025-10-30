package com.zhuxi.product.module.interfaces.vo;

import com.zhuxi.product.module.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商品详情VO")
public class ProductDetailVO {
    @Schema(description = "商品编号")
    private String productSn;
    @Schema(description = "卖家编号")
    private String sellerSn;
    @Schema(description = "卖家名称")
    private String sellerName;
    @Schema(description = "卖家头像")
    private String sellerAvatar;
    @Schema(description = "商品名称")
    private String title;
    @Schema(description = "商品价格")
    private BigDecimal price;
    @Schema(description = "商品成色id")
    private Integer conditionId;
    @Schema(description = "商品成色名称")
    private String conditionName;
    @Schema(description = "商品分类id")
    private String categoryId;
    @Schema(description = "商品分类名称")
    private String categoryName;
    @Schema(description = "商品状态")
    private ProductStatus status;
    @Schema(description = "商品所在地区")
    private String location;
    @Schema(description = "商品描述")
    private String description;
    @Schema(description = "商品浏览次数")
    private Long viewCount;
    @Schema(description = "商品热度指数")
    private Long hostScore;
    @Schema(description = "商品图片列表")
    private List<ProductImage> productImages;
}
