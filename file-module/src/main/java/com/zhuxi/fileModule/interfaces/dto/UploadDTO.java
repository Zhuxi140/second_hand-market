package com.zhuxi.fileModule.interfaces.dto;

import com.zhuxi.common.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "上传文件参数")
public class UploadDTO {
    @Schema(description = "文件归属类型", example = "product、avatar、spec、icon", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String category;
    @Schema(description = "mime类型", example = "image/jpeg", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String mime;
    @Schema(description = "文件大小（单位为MB）", example = "10 ", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = ValidationMessage.NOT_NULL)
    private Long size;
    @Schema(description = "唯一标识号（如商品文件 则传商品唯一标识号  如头像 则传账户唯一标识号,如果为分类图标 则为id）", example = "FILE202401010001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String sn;
}
