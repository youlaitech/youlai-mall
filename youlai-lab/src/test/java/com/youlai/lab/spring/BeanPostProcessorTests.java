package com.youlai.lab.spring;

import com.youlai.lab.spring.beanPostProcessor.*;
import com.youlai.lab.spring.beanPostProcessor.Bean;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *  测试beanPostProcessor的作用和执行时机
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/19 0019 14:20
 */
public class BeanPostProcessorTests {

    /**
     * spring内部实现的beanPostProcessos,用来创建bean
     */
    @Test
    void beanPostProcessor(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                Bean.class,IBeanPostProcessor.class,  IInstantiationAwareBeanPostProcessor.class,
                ISmartInstantiationAwareBeanPostProcessor.class,IDestructionAwareBeanPostProcessor.class,
                IMergedBeanDefinitionPostProcessor.class
                );
        context.close();
    }

    /**
     * 常用的bean生命周期回调
     */
    @Test
    void avdiceTests(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(IBeanPostProcessor.class);
        context.register(IInitializingBean.class);
        context.register(IDisposableBean.class);
        context.register(Bean.class);
        context.getBeanDefinition("bean").setLazyInit(true);
        context.refresh();
        System.out.println("\n\n-------------------开始创建bean--------------------");
        context.getBean(Bean.class);
        context.close();
    }
}
