package com.youlai.lab.spring.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * beanPostProcessor主要用于干预Bean的创建过程
 * spring内部有很多实现,如DestructionAwareBeanPostProcessor,
 * InstantiationAwareBeanPostProcessor,SmartInstantiationAwareBeanPostProcessor
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/19 0019 14:08
 */

public class IBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("9---自定义初始化前调用"+beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("10---自定义初始化后调用"+beanName);
        return null;
    }
}
