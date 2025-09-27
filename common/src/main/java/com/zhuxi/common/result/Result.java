package com.zhuxi.common.result;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果封装类
 * @param <T> 响应数据类型
 * @author zhuxi
 */
@Schema(description = "统一响应结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    @Schema(description = "响应状态码", example = "200")
    private int code;
    
    @Schema(description = "响应消息", example = "success")
    private String message;
    
    @Schema(description = "响应数据")
    private T data;
    
    @Schema(description = "响应时间戳", example = "1640995200000")
    private long timestamp;

    /**
     * 通用成功
     */
    public static <T> Result<T> success(String message) {
        if(message == null){
            message = "success";
        }
        return new Result<>(200, message,null, System.currentTimeMillis());
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "message",null, System.currentTimeMillis());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data, System.currentTimeMillis());
    }

    public static <T> Result<T> success(String message, T data,int code) {
        if(message == null){
            message = "success";
        }
        return new Result<>(code, message, data, System.currentTimeMillis());
    }

    /**
     *通用失败
     */
    public static <T> Result<T> fail(String message){
        if(message == null){
            message = "fail";
        }
        return new Result<>(500, message, null, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(){
        return new Result<>(500, "fail", null, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(T data){
        return new Result<>(500, "fail", data, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(String message, T data){
        if(message == null){
            message = "fail";
        }
        return new Result<>(500, message, data, System.currentTimeMillis());
    }

    /**
     * 成功(包含提示信息)
     */
    public static <T> Result<T> success(String message, T data){
        if (message == null){
            message = "success";
        }
        return new Result<>(200, message, data, System.currentTimeMillis());
    }

    /**
     * 失败(包含提示信息)
     */
    public static <T> Result<T> fail(int code, String message){
        if (message == null){
            message = "fail";
        }
        return new Result<>(code, message, null, System.currentTimeMillis());
    }
}
