package com.youlai.common.core.annotation;


import java.lang.annotation.*;

/**
 * 防重提交注解
 *
 * @author Ray.Hao
 * @since 2023/5/9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepeatSubmit {

    /**
     * 防重提交锁过期时间(秒)
     * <p>
     * 默认5秒内不允许重复提交
     */
    int expire() default 5;

}
