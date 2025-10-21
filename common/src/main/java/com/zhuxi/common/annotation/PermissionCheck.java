package com.zhuxi.common.annotation;

import com.zhuxi.common.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhuxi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {
    Role Role();

    String toRole() default "user";

    boolean enableDataOwnership() default false;

    /**
     * 禁用权限自动继承 即关闭高角色权限自动继承低角色权限 如
     */
    boolean disableOverLevel() default false;

    String snParam() default "userSn";
}
