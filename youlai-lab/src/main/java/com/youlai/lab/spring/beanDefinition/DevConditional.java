package com.youlai.lab.spring.beanDefinition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 *
 *  实现注入条件
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-02-24 17:32
 */
public class DevConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String property = context.getEnvironment().getProperty("java.version");
        return Objects.equals(property, "1.8.0_312");
    }
}
