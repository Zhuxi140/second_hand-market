package com.zhuxi.user.module.interfaces.dto.user;

import com.zhuxi.common.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "刷新令牌请求数据")
public class RefreshDTO {

    @Schema(description = "刷新令牌", example = "weadsadgvdsbfdgesfszhcvixuvklxcn...")
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String refreshToken;
    @Schema(description = "用户编号", example = "USR202401010001")
    @NotBlank(message = ValidationMessage.NOT_NULL_NOT_EMPTY)
    private String userSn;
}
