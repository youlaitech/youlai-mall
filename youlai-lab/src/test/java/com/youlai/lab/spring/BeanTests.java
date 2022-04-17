package com.youlai.lab.spring;

import com.youlai.lab.spring.bean.*;
import com.youlai.lab.spring.bean.aware.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * bean测试用例
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-09 13:08
 */
public class BeanTests {


    /**
     * 使用FactoryBean创建bean
     */
    @Test
    void factoryBean(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/bean/CarFactoryBean.xml");
//        Object carFactoryBean = context.getBean("&car");   //获取创建bean的工厂
//        System.out.println(carFactoryBean);
        Object car = context.getBean("car");
        System.out.println(car);
    }

    /**
     * 容器开始和结束的回调接口
     * Lifecycle接口必须显示调用容器start方法,才能执行预先定义的回调
     * start的spring底层调用{@link org.springframework.context.support.DefaultLifecycleProcessor#doStart}
     * stop的spring底层调用{@link org.springframework.context.support.DefaultLifecycleProcessor#doStop}
     */
    @Test
    void lifecycle(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ILifecycle.class);
        context.start();
        context.close();
    }

    /**
     * 容器开始和结束的回调接口
     * {@link org.springframework.context.SmartLifecycle}继承了Lifecycle和Phased,
     * 相比Lifecycle多了自定义优先级功能和控制是否需要显示调用start
     *
     */
    @Test
    void smartLifecycle(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ISmartLifecycle1.class, ISmartLifecycle2.class);
        context.close();
    }

    /**
     * 先执行BeanNameAware,BeanClassLoaderAware,BeanFactoryAware三个aware,再执行其他aware
     * spring底层实现:{@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#invokeAwareMethods}
     */
    @Test
    void aware(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IBeanNameAware.class, IBeanClassLoaderAware.class, IBeanFactoryAware.class);
    }

    /**
     * 处理上面三个aware再执行其他aware
     *参考{@link org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces}
     */
    @Test
    void atherAware(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IApplicationContextAware.class, IApplicationStartupAware.class, IEnvironmentAware.class, IEmbeddedValueResolverAware.class);
    }

    /**
     * 执行完aware接口后执行初始化方法
     * 先执行实现InitializingBean接口的afterPropertiesSet方法,后执行initMethod方法
     */
    @Test
    void initializingBean(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IInitializingBean.class);
    }


    /**
     * 初始化方法执行完之后执行初始化后方法,AOP在这个地方完成
     * 参考{@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization}
     */
    @Test
    void afterInitialization(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IInitializingBean.class,IBeanPostProcessor.class);
    }




}
