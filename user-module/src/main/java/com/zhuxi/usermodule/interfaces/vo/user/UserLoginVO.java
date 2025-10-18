package com.zhuxi.usermodule.interfaces.vo.user;

import com.zhuxi.common.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录响应视图对象
 * @author zhuxi
 */
@Schema(description = "用户登录响应数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {
    @Schema(description = "用户唯一标识号", example = "USR202401010001")
    private String userSn;
    
    @Schema(description = "用户昵称", example = "换换1234")
    private String nickname;
    
    @Schema(description = "用户角色", example = "user")
    private Role role;
    
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
    
    @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    
    @Schema(description = "刷新令牌", example = "weadsadgvdsbfdgesfszhcvixuvklxcn...")
    private String refreshToken;
    
    @Schema(description = "刷新令牌过期时间", example = "1640995200000")
    private Long refreshExpireTime;

    @Schema(description = "访问令牌过期时间", example = "1640995200000")
    private Long accessExpireTime;
}
