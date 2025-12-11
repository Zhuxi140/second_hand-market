package com.zhuxi.product.module.interfaces.dto.order;

import lombok.Data;

@Data
public class UpdateOrderStatusDTO {
    private Integer userStatus;
    private String remark;
}
