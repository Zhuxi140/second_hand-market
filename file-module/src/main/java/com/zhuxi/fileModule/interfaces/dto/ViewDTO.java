package com.zhuxi.fileModule.interfaces.dto;

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
@Schema(description = "获取文件查看签名外层请求包装类")
public class ViewDTO {
    @Schema(description = "文件查看参数列表")
    private List<ViewDTOParam> views;
}
