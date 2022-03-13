package com.youlai.laboratory.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
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

    void createBean(){
         new DefaultListableBeanFactory();
    }

}
