package com.youlai.lab.spring;

import com.youlai.lab.spring.constructor.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 推断构造方法
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-14 8:50
 */
public class ConstructorTests {


    /**
     * 默认使用无参构造函数
     */
    @Test
    void wu(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class);
    }

    /**
     *  如果指定Autowired,则使用autowired标注的构造函数
     */
    @Test
    void autowired(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class,B.class);
    }

    /**
     * 自定义实例化方法{@link java.util.function.Supplier#get}
     * 源码调用{@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBeanInstance}
     */
    @Test
    void supplier(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(C.class,new C());
        context.refresh();
       context.getBean("c");
    }

    /**
     * 如果指定了工厂方法，则使用工厂方法创建创建
     */
    @Test
    void factoryBean(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/constructor/Bean.xml");
         Object d = context.getBean("d");
    }


    /**
     * 多个@Autowired标注构造器,必须设置required为false,根据能找到的最多的参数bean选择构造器
     */
    @Test
    void autowired2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A.class, E.class);
    }

    /**
     * 当有多个构造器时,根据计算匹配度得分选择构造器，选择得分低的
     * 完全匹配，得分为0
     * 父类得分为2
     * 父类的父类得分为4
     * 接口得分为1
     */
    @Test
    void constructor(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext( F.class,G.class);
    }

}
