package com.zhuxi.product.module.interfaces.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(description = "获取个人发布商品列表")
public class MeShProductVO {
    @Schema(description = "商品编号")
    private String productSn;
    @Schema(description = "商品标题")
    private String title;
    @Schema(description = "商品价格")
    private BigDecimal price;
    @Schema(description = "商品封面图片")
    private String imageUrl;
    @Schema(description = "商品浏览次数")
    private Long viewCount;
    @Schema(description = "是否是草稿")
    private Integer isDraft;
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createAt;
}
