package com.youlai.lab.spring.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/17 0017 21:59
 */

public class B implements BeanDefinitionRegistryPostProcessor,PriorityOrdered {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("B------------BeanDefinitionRegistryPostProcessor---PriorityOrdered----postProcessBeanDefinitionRegistry------1");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("B------------BeanDefinitionRegistryPostProcessor---PriorityOrdered----postProcessBeanFactory------4");
    }
}
