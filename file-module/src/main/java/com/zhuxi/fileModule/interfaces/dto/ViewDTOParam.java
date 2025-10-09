package com.zhuxi.fileModule.interfaces.dto;

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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件查看响应体")
public class ViewDTOParam {
    @Schema(description = "文件路径")
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String fileKey;
    @Schema(description = "文件归属")
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String category;
}
