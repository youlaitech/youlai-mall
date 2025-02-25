package com.youlai.common.core.annotation;

import com.youlai.common.enums.LogModuleEnum;
import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author Ray.Hao
 * @since 2024/6/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Log {

    String value() default "";

    LogModuleEnum module()  ;


}