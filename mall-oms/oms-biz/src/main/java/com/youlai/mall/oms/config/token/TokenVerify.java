package com.youlai.mall.oms.config.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huawei
 * @desc Token 幂等性校验接口注解
 * 幂等性方案：注解 + 拦截器
 * 注解：@TokenVerify 注解，该注解有两个属性，generate 和 clean
 * generate：属性为true是表示请求该方法会生成一个uuid token，同时放入redis中和用户请求中，用于用户下一次请求校验，默认为false
 * clean：如果属性为true表示请求该方法需要校验token唯一性，并且在校验通过后清除redis中的token
 * 拦截器：TokenVerifyHandler
 * 实现原理：拦截所有的请求，如果该请求被 @TokenVerify 注解标识：首先判断该注解属性 generate 是否为true，然后校验属性 clean 是否为true
 * 最后，在 WebMvcConfig 配置中配置拦截器
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenVerify {

    // 是否生成校验token
    boolean generate() default false;

    // 是否校验token
    boolean verify() default false;

}
