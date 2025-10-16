package com.zhuxi.common.handler;

import com.zhuxi.common.constant.ValidationMessage;
import com.zhuxi.common.exception.*;
import com.zhuxi.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


/**
 * @author zhuxi
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleException(MethodArgumentNotValidException e)
    {
        String location = getLocation(e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult ==  null){
            log.error(e.getMessage() + "--location: {}",location);
            return Result.fail("未知错误");
        }

        String message = bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    String field = error.getField();
                    String errorMessage = error.getDefaultMessage();
                    if (errorMessage == null || errorMessage.isEmpty()) {
                        return field + "：未知错误";
                    }
                    return switch (errorMessage) {
                        case ValidationMessage.NOT_NULL -> field + "不能为空";
                        default ->  errorMessage;
                    };
                }).collect(Collectors.joining("；"));

        log.error(e.getMessage() + "--location: {}", location);
        return Result.fail(message);
    }


    @ExceptionHandler({BusinessException.class, PersistenceException.class, TokenException.class, COSException.class})
    public Result<String> handleException(LocationException e)
    {
        String message = e.getMessage();
        String location = e.getLocation();
        log.error(message + "--location: {}", location);
        return Result.fail(message);
    }

    private String getLocation(Exception  e){
        String location = "无位置信息";
        StackTraceElement[] stackTrace = e.getStackTrace();
        if(stackTrace.length > 0){
            StackTraceElement stackTraceElement = stackTrace[0];
            return String.format("Class:%s,Method:%s,Line:%d",
                    stackTraceElement.getClassName(),
                    stackTraceElement.getMethodName(),
                    stackTraceElement.getLineNumber()
            );
        }
        return location;
    }
}
