package com.zhuxi.userModule.interfaces.dto.user;

import com.zhuxi.common.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录请求DTO
 * @author zhuxi
 */
@Schema(description = "用户登录请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @Schema(description = "用户名", example = "testuser", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "用户名长度需在5-20字符之间")
    private String username;
    
    @Schema(description = "密码", example = "password123", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码至少需要8位，包含字母和数字"
    )
    private String password;
}
