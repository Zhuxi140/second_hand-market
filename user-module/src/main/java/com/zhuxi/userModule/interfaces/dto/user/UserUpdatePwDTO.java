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
 * 用户密码修改请求DTO
 * @author zhuxi
 */
@Schema(description = "用户密码修改请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePwDTO {
    @Schema(description = "原密码", example = "oldpassword123", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "密码长度需在5-20字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码至少需要8位，包含字母和数字"
    )
    private String oldPassword;
    
    @Schema(description = "新密码", example = "newpassword123", required = true)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    @Size(min=5,max=20, message = "密码长度需在5-20字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "密码至少需要8位，包含字母和数字"
    )
    private String newPassword;
}
