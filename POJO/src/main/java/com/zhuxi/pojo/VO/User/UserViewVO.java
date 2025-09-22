package com.zhuxi.pojo.VO.User;

import com.zhuxi.common.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewVO {
    private String userSn;
    private String username;
    private String nickname;
    private String gender;
    private String phone;
    private String email;
    private String avatar;
    private Role role;
    private int status;
    private LocalDateTime createdAt;
}
