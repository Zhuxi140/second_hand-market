package com.zhuxi.common.constant;

/**
 * @author zhuxi
 */
public class AuthMessage {

    public static final String JWT_IS_NULL_OR_EMPTY = "您尚未登录，请先登录";
    public static final String LOGIN_INVALID = "登录状态已失效，请重新登录";
    public static final String LOGIN_EXPIRED = "登录状态已过期，请重新登录";
    public static final String OTHER_TOKEN_ERROR = "系统繁忙，请稍后重试";
    public static final String REQUEST_CONTEXT_NOT_FOUND = "请求或数据异常，请联系管理员或稍后重试";
    public static final String NO_PERMISSION = "您没有权限访问此资源";
}
