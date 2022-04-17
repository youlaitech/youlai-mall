package com.youlai.lab.spring.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 *
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/17 0017 22:03
 */

public class D implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("D------------BeanFactoryPostProcessor---postProcessBeanFactory----9");
    }

}
