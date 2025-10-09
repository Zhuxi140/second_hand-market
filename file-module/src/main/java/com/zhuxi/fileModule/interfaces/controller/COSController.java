package com.zhuxi.fileModule.interfaces.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.fileModule.interfaces.dto.ConfirmDTO;
import com.zhuxi.fileModule.interfaces.dto.UploadDTO;
import com.zhuxi.fileModule.application.service.FileServiceImpl;
import com.zhuxi.fileModule.interfaces.dto.ViewDTO;
import com.zhuxi.fileModule.interfaces.vo.ViewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

/**
 * @author zhuxi
 */
@RestController
@RequestMapping("/cos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "COS管理")
public class COSController {

    private final FileServiceImpl fileServiceImpl;


    @Operation(summary = "获取上传凭证",description = "获取预签名URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "失败，可能原因: 文件类型错误 不支持该文件类型 文件过大等")
    })
    @PostMapping("/upload")
    public Result<URL> upload(@Validated @RequestBody UploadDTO upload) {
        URL url = fileServiceImpl.uploadFile(upload);
        if (url != null){
            return Result.success(url);
        }
        return Result.fail();
    }

    @Operation(summary = "确认文件上传状态",description = "确认文件上传状态")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "失败，可能原因: 文件类型错误 不支持该文件类型 数据异常等")
    })
    @PostMapping("/confirm")
    public Result<String> confirm(@Validated @RequestBody ConfirmDTO confirm) {
        fileServiceImpl.confirmFile(confirm);
        return Result.success();
    }

    @Operation(summary = "获取文件查看签名",description = "获取文件查看签名")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "失败，可能原因: 文件类型错误 不支持该文件类型 权限不足 数据异常等")
    })
    @PostMapping("/view")
    public Result<ViewVO> view(@Validated @RequestBody ViewDTO view) {
        ViewVO viewVO = fileServiceImpl.getViewUrl(view);
        if (viewVO != null){
            return Result.success(viewVO);
        }
        return Result.fail();
    }
}
