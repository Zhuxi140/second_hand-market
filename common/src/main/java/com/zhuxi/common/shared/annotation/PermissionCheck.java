package com.zhuxi.common.shared.annotation;

import com.zhuxi.common.shared.enums.Role;

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
    /**
     * 所需的角色
     */
    Role Role();

    /**
     * 所需的权限
     */
    String permission();

    /**
     * 权限逻辑关系
     * AND: role和permission都需满足
     * OR: 满足role和permission中的任意一个
     * 默认： OR
     */
    Logic logic() default Logic.OR;

    /**
     * 是否启用验证数据所有权限
     */
    boolean enableDataOwnership() default false;

    /**
     * 数据所有权需效验的方法中的参数名称
     */
    String snParam() default "userSn";


    enum Logic{
        AND,
        OR,
    }
}
