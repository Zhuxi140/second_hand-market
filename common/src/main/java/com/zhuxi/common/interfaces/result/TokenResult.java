package com.zhuxi.common.interfaces.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "访问令牌信息")
public class TokenResult {
    @Schema(description = "访问令牌")
    private String accessToken;
    @Schema(description = "访问令牌过期时间")
    private Long accessExpireTime;
}
