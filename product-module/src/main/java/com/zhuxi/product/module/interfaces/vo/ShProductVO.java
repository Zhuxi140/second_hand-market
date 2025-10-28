package com.zhuxi.product.module.interfaces.vo;

import com.zhuxi.product.module.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "二手商品VO")
public class ShProductVO {
    @Schema(description = "商品编号")
    private String productSn;
    @Schema(description = "卖家编号")
    private String userSn;
    @Schema(description = "卖家昵称")
    private String nickName;
    @Schema(description = "卖家头像url")
    private String avatar;
    @Schema(description = "商品封面图片url")
    private String imageUrl;
    @Schema(description = "商品名称")
    private String title;
    @Schema(description = "商品价格")
    private BigDecimal price;
    @Schema(description = "商品状态")
    private ProductStatus status;
    @Schema(description = "商品位置")
    private String location;
    @Schema(description = "商品创建时间")
    private LocalDateTime createdAt;
}
