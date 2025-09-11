package com.zhuxi.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Admin {
    private Long id;
    private String role;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private String idNumber;
    private Integer status;
    private Long createId;
    private Long updateId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
