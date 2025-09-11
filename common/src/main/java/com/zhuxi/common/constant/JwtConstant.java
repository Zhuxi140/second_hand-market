package com.zhuxi.common.constant;

import lombok.Data;

@Data
public class JwtConstant {
    private Long userId;
    private String adminId;
    private String userName;
    private String role;
}
