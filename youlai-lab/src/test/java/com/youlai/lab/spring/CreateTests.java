package com.youlai.lab.spring;


import com.youlai.lab.spring.create.ConstructorService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * 三种实例化bean方式
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/18 0018 23:19
 */
public class CreateTests {

    @Test
    void constructor(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConstructorService.class);
    }

    @Test
    void factoryMethod(){
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/create/FactoryClass.xml");
    }

    @Test
    void factoryBean(){
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/create/FactoryBean.xml");
    }
}
