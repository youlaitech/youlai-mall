package com.youlai.common.mybatis.handler;

import java.lang.annotation.*;

/**
 * 是否需要数据权限
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2021-12-10 15:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface InterceptorIgnore {

    /**
     * 数据权限 {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
     * <p>
     * 默认打开，需要注解关闭
     */
    String dataPermission() default "0";

    String storeAlias() default "";
}

