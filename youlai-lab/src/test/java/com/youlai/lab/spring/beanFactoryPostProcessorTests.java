package com.youlai.lab.spring;

import com.youlai.lab.spring.beanFactoryPostProcessor.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *  BeanFactoryPostProcessor用于操作BeanDefinition信息,在实例化bean之前操作
 * 测试BeanFactoryPostProcessor执行顺序
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/17 0017 21:56
 */
public class beanFactoryPostProcessorTests {

    /**
     *
     *  参考实现 {@link org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors}
     *  执行顺序
     *  1:实现了BeanDefinitionRegistryPostProcessor和PriorityOrdered接口的类的postProcessBeanDefinitionRegistry方法
     *  2:实现了BeanDefinitionRegistryPostProcessor和Ordered接口的类的postProcessBeanDefinitionRegistry方法
     *  3:实现了BeanDefinitionRegistryPostProcessor接口的类的postProcessBeanDefinitionRegistry方法
     *  4:实现了BeanDefinitionRegistryPostProcessor和PriorityOrdered接口的类的postProcessBeanFactory方法
     *  5:实现了BeanDefinitionRegistryPostProcessor和Ordered接口的类的postProcessBeanFactory方法
     *  6:实现了BeanDefinitionRegistryPostProcessor接口的类的postProcessBeanFactory方法
     *  7:实现了BeanFactoryPostProcessor和PriorityOrdered接口的类的postProcessBeanFactory方法
     *  8:实现了BeanFactoryPostProcessor和Ordered接口的类的postProcessBeanFactory方法
     *  9:实现了BeanFactoryPostProcessor接口的类的postProcessBeanFactory方法
     *
     *  总结 BeanDefinitionRegistryPostProcessor>BeanFactoryPostProcessor
     *      PriorityOrdered>Ordered
     */
    @Test
    public void A(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(A.class, B.class, C.class, E.class, D.class,F.class);
    }
}
