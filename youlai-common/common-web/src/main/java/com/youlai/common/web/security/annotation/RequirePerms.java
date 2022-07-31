package com.youlai.common.web.security.annotation;

import java.lang.annotation.*;

/**
 * 权限校验注解
 *
 * @author haoxr
 * @Date 2022/7/30
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePerms {

    String[] value() default {};

}

