package com.youlai.laboratory.spring;

import com.youlai.laboratory.spring.beanDefinition.Property;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * beandefinition属性测试
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/21 0021 7:02
 */
@SpringBootTest
public class BeanDefinition {

    @Test
    void scope(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Object scope = context.getBean("scope");
        Object scope1 = context.getBean("scope");
        System.out.println(scope.equals(scope1));
    }

    @Test
    void prototype(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Object prototype = context.getBean("prototype");
        Object prototype1 = context.getBean("prototype");
        System.out.println(prototype.equals(prototype1));
    }

    @Test
    void lazy(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        System.out.println("-----加载配置结束------");
        context.getBean("lazy");   //获取bean的时候创建bean
    }


}
