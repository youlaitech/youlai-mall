package com.youlai.lab.spring;


import com.youlai.lab.spring.look.constructors.Config1;
import com.youlai.lab.spring.look.prototype.B3;
import com.youlai.lab.spring.look.prototype.Config3;
import com.youlai.lab.spring.look.setter.A2;
import com.youlai.lab.spring.look.setter.Config2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 循环依赖测试用例
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-09 10:26
 */
public class LookTests {

    /**
     * 构造器方式注入的循环依赖spring无法解决
     * 因为spring需要依赖构造器来实施例bean
     */
    @Test
    void constructor(){
        try{
           new AnnotationConfigApplicationContext(Config1.class);
        }catch (UnsatisfiedDependencyException exception){
            exception.getCause();
        }
    }

    /**
     * set方式存入的单例bean,spring使用三级缓存通过提前暴露实例对象地址解决循环依赖
     * 一级缓存已经注册的bean: {@link org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.singletonObjects}
     * 二级缓存已经实例化但未初始化的bean: {@link org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.earlySingletonObjects}
     * 三级缓存正在实例化的工厂bean:{@link org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.singletonFactories}
     * 因为AOP创建代理对象依赖原始对象所以需要第三级缓存
     */
    @Test
     void setter(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config2.class);
        A2 a2 = context.getBean("a2", A2.class);
        System.out.println(a2);
    }

    /**
     * 原型模式的bean,因为每次都要创建新的bean,所以spring无法通过提前暴露原始对象的bean来解决循环依赖
     */
    @Test()
    void prototype(){
        try{
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
            System.out.println(context.getBean("b3", B3.class));
        }catch (UnsatisfiedDependencyException e){
            e.getCause();
        }

    }

}
