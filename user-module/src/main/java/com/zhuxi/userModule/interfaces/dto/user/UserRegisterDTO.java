package com.zhuxi.userModule.interfaces.dto.user;

import com.zhuxi.common.constant.ValidationMessage;
import com.zhuxi.common.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册请求DTO
 * @author zhuxi
 */
@Schema(description = "用户注册请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    @Schema(description = "用户唯一标识号", example = "USR202401010001", hidden = true)
    private String userSn;
    
    @Schema(description = "用户名", example = "testuser", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "用户名长度需在5-20字符之间")
    private String username;
    
    @Schema(description = "密码", example = "password123",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
    message = "密码至少需要8位，包含字母和数字"
    )
    private String password;
    
    @Schema(description = "手机号", example = "13800138000",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;
    
    @Schema(description = "用户角色", example = "user", defaultValue = "user")
    private Role role = Role.user;
    
    @Schema(description = "邮箱地址", example = "test@example.com")
    private String email;
    
    @Schema(description = "用户昵称", example = "换换1234")
    @Size(min =2,max=20, message = "昵称长度不能超过20字符或低于2个字符")
    private String nickname;


}
