package com.zhuxi.fileModule.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

/**
 * @author zhuxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文件查看响应体")
public class ViewVOParam {

    @Schema(description = "文件路径")
    private String fileKey;
    @Schema(description = "文件查看URL")
    private URL url;
    @Schema(description = "url过期时间")
    private Long expireTime;
}
