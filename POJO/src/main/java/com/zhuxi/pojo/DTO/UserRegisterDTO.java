package com.zhuxi.pojo.DTO;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String nickname;
}
