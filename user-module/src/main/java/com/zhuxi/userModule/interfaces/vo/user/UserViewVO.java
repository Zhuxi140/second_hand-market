package com.zhuxi.userModule.interfaces.vo.user;

import com.zhuxi.common.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息视图对象
 * @author zhuxi
 */
@Schema(description = "用户信息响应数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewVO {
    @Schema(description = "用户唯一标识号", example = "USR202401010001")
    private String userSn;
    
    @Schema(description = "用户名", example = "testuser")
    private String username;
    
    @Schema(description = "用户昵称", example = "换换1234")
    private String nickname;
    
    @Schema(description = "性别", example = "男")
    private String gender;
    
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    @Schema(description = "邮箱地址", example = "test@example.com")
    private String email;
    
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
    
    @Schema(description = "用户角色", example = "user")
    private Role role;
    
    @Schema(description = "用户状态", example = "1")
    private int status;
    
    @Schema(description = "创建时间", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
}
