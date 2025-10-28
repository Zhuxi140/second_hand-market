package com.zhuxi.product.module.interfaces.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhuxi.common.shared.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@Schema(description = "二手商品查询接口参数")
public class ShProductParam {
    @Schema(description = "每页数量，默认为20",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 10,message = "每页数量至少为10个")
    @Max(value = 100,message = "每页数量最多为100个")
    private Integer pageSize = 20;
    @Schema(description = "上一页最后一个商品编号",requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastSn;
    @Schema(description = "上一页最后一个商品价格",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @DecimalMin(value = "0",message = "价格不能小于0")
    private BigDecimal lastPrice;
    @Schema(description = "上一页最后一个商品评分",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 0,message = "热度指标不能小于0")
    private Long lastHostScore;
    @Schema(description = "上一页最后一个商品浏览量",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 0,message = "浏览量不能小于0")
    private Long lastViewCount;
    @Schema(description = "排序字段（搭配lastPrice、lastHostScore、lastViewCount使用）",example = "price、hostScore、viewCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "price|hostScore|viewCount|null",message = "排序字段不合法")
    private String sortField;
    @Schema(description = "排序顺序(DESC或ASC)",example = "DESC、ASC", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "DESC|ASC",message = "排序顺序不合法")
    @NotBlank(message = "sortOrder" + ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String sortOrder;
    @Schema(description = "商品位置(筛选字段)",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String location;
    @Schema(description = "商品成色(筛选字段)",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long conditionId;
    @Schema(description = "商品创建时间(保底默认字段)",requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastCreatedAt;
    @JsonIgnore
    private Long productId;

    public ShProductParam(Integer pageSize, String lastSn, BigDecimal lastPrice, Long lastHostScore, Long lastViewCount, String sortField, String sortOrder, String location, Long conditionId, LocalDateTime lastCreatedAt) {
        this.pageSize = pageSize;
        this.lastSn = lastSn;
        this.lastPrice = lastPrice;
        this.lastHostScore = lastHostScore;
        this.lastViewCount = lastViewCount;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.location = location;
        this.conditionId = conditionId;
        this.lastCreatedAt = lastCreatedAt;
    }
}
