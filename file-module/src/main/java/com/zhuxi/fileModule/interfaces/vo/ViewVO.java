package com.zhuxi.fileModule.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhuxi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件查看响应外层包装类")
public class ViewVO {

    @Schema(description = "文件查看响应体列表")
    private List<ViewVOParam> views;
}
