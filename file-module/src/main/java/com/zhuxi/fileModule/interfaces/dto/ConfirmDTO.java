package com.zhuxi.fileModule.interfaces.dto;

import com.zhuxi.common.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "确认文件上传状态参数")
public class ConfirmDTO {

    @Schema(description = "唯一标识号", example = "FILE202401010001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String sn;
    @Schema(description = "文件归属类型", example = "product、avatar、spec", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String category;
    @Schema(description = "mime类型", example = "image/jpeg", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = ValidationMessage.NOT_NULL)
    private String mime;
    @Schema(description = "类型(1封面 2商品详细图 3规格图),category为avatar时 该字段填多少都无所谓")
    @NotNull(message = ValidationMessage.NOT_NULL)
    private int fileType;
}
