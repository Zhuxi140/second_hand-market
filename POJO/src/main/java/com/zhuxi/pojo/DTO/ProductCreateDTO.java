package com.zhuxi.pojo.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductCreateDTO {
    private String title;
    private String description;
    private Long categoryId;
    private BigDecimal price;
    private Integer condition;
    private String location;
    private Long sellerId;
}
