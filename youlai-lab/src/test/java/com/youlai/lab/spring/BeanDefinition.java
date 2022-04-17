package com.youlai.lab.spring;

import com.google.common.base.Objects;
import com.youlai.lab.spring.beanDefinition.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * beandefinition属性测试
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/21 0021 7:02
 */
public class BeanDefinition {

    @Autowired
    @Qualifier("scope")
    private Bean bean;

    @Test
    void scope() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Object scope1 = context.getBean("scope");
        Object scope2 = context.getBean("scope");
        System.out.println(scope1.equals(scope2));     //获取两个相同的bean
    }

    /**
     * 原型模式的bean
     */
    @Test
    void prototype() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Object prototype1 = context.getBean("prototype");
        Object prototype2 = context.getBean("prototype");
        System.out.println(prototype1.equals(prototype2));   //获取两个不同的bean
    }

    /**
     * 懒加载的bean
     */
    @Test
    void lazy() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        System.out.println("-----加载配置结束------");
        context.getBean("lazy");   //获取bean的时候才创建bean
    }

    /**
     * 多个相同类型的bean,声明默认的bean
     */
    @Test
    void primary() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Bean bean = context.getBean(Bean.class, Bean.class);   //如果注册多个相同类型的bean,不指定name的情况下获取primary的bean
        System.out.println(bean);
    }

    /**
     *  依赖bean
     */
    @Test
    void dependsOn() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Bean depends = context.getBean("depends", Bean.class);   //depends依赖beanB,所以会在创建depends之前先创建beanB
    }

    @Test
    void initMethodNameAndDestroyMethodNameA() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanA.class);
        System.out.println("准备销毁bean");
        context.close();
    }

    @Test
    void initMethodNameAndDestroyMethodNameB() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanB.class);
        System.out.println("准备销毁bean");
        context.close();
    }

    @Test
    void initMethodNameAndDestroyMethodNameC() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beanDefinition/Property.xml");
        System.out.println("准备销毁bean");
        context.close();
    }

    /**
     * 父子beanDefinition合并，参考:
     * {@link org.springframework.beans.factory.support.AbstractBeanFactory#getMergedBeanDefinition(String beanName, org.springframework.beans.factory.config.BeanDefinition bd,org.springframework.beans.factory.config.BeanDefinition containingBd)}
     */
    @Test
    void parent() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beanDefinition/Property.xml");
        Bean bean = context.getBean("parent", Bean.class);
        System.out.println(bean);   //会合并父属性的值，但不会覆盖值
    }

    /**
     * 条件注入，需要满足当前jdk版本为<1.8.0_312>
     */
    @Test
    void condition() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        List<String> list = Arrays.stream(context.getBeanDefinitionNames()).filter(name -> name.equals("condition")).collect(Collectors.toList());
        System.out.println(list.isEmpty());
    }

    /**
     * 使用import注册bean
     */
    @Test
    void importBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        System.out.println(context.getBean(ImportBean.class));
    }

    /**
     * 注入指定名称的bean
     */
    @Test
    void qualifier(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Property.class);
        Bean scope = context.getBean("scope", Bean.class);
        System.out.println(Objects.equal(scope,bean));
    }

    /**
     * 根据环境注入的bean
     */
    @Test
    void profile(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("prod");
        //context.getEnvironment().setActiveProfiles("dev");
        context.register(Property.class);
        context.refresh();
        Bean profile = context.getBean("profile", Bean.class);
    }

    /**
     * 自定义xml标签
     */
    @Test
    void parseXml(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/youlai-user.xml");
        User user = applicationContext.getBean("userBean", User.class);
        System.out.println(user);
    }






}
