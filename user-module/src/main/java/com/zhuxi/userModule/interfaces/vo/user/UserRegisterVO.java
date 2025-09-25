package com.zhuxi.userModule.interfaces.vo.user;

import com.zhuxi.userModule.domain.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterVO {
    private String userSn;
    private String nickname;
    private Role role;
    private String avatar;
    private String accessToken;
    private String refreshToken;
    private Long expireTime;

}
