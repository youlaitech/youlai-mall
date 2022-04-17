package com.youlai.lab.spring.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * Bean销毁时的回调
 * DestructionAwareBeanPostProcessor
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/19 0019 20:21
 */
public class IDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        System.out.println("12---在Bean被销毁前调用"+beanName);
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        System.out.println("11---判断是否需要被销毁,默认都需要"+bean);
        return true;
    }
}
