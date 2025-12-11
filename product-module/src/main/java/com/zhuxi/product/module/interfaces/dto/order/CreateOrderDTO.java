package com.zhuxi.product.module.interfaces.dto.order;

import lombok.Data;

@Data
public class CreateOrderDTO {
    private String buyerSn;
    private String sellerSn;
    private String productSn;
    private Integer quantity;
    private String addressSn;
    private String remark;
}
