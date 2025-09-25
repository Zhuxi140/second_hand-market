package com.zhuxi.userModule.domain.user.model;

import com.zhuxi.userModule.domain.user.enums.Role;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String userSn;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
    private Role role;
}
