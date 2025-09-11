package com.zhuxi.common.result;


import com.zhuxi.common.constant.CommonMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;

    /**
     * 通用成功
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, CommonMessage.OPERATE_SUCCESS, data, System.currentTimeMillis());
    }

    /**
     *通用失败
     */
    public static <T> Result<T> fail(String message){
        return new Result<>(500, message, null, System.currentTimeMillis());
    }

    /**
     * 成功(包含提示信息)
     */
    public static <T> Result<T> success(String message, T data){
        return new Result<>(200, message, data, System.currentTimeMillis());
    }

    /**
     * 失败(包含提示信息)
     */
    public static <T> Result<T> fail(int code, String message){
        return new Result<>(code, message, null, System.currentTimeMillis());
    }
}
