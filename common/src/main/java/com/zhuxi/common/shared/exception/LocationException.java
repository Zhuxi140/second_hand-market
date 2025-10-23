package com.zhuxi.common.shared.exception;

public class LocationException extends RuntimeException {
    public LocationException(String message) {
        super(message);
    }
    public String getLocation(){
        StackTraceElement[] stackTrace = getStackTrace();
        if(stackTrace.length > 0){
            StackTraceElement stackTraceElement = stackTrace[0];
            return String.format("Class:%s,Method:%s,Line:%d",
                    stackTraceElement.getClassName(),
                    stackTraceElement.getMethodName(),
                    stackTraceElement.getLineNumber()
                    );
        }
        return "无异常位置信息";
    }
}
