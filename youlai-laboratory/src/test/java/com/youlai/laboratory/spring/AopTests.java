package com.youlai.laboratory.spring;


import com.youlai.laboratory.spring.aop.proxyFactory.*;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Aop测试类
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/25 0025 23:42
 */
public class AopTests {

    /**
     * 手动代理
     * 使用proxyFactory通过编程创建AOP代理
     */
    @Test
    void proxyFactory(){
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(new UserService());
        factory.addAdvisor(new MyAdvisor());
        UserService userService = (UserService) factory.getProxy();
        userService.test();
    }

    /**
     * 定义自动代理器,通过名称自动代理
     */
    @Test
    void beanNameAutoProxyCreator(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyBeanNameAutoProxyCreator.class, MyAdvisor.class, UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.test();
    }

    /**
     * 注册默认代理
     * 只要有defaultAdvisorAutoProxyCreator这个bean,它就会自动识别所有Advisor中的PointCut进行代理
     */
    @Test
    void defaultAdvisorAutoProxyCreator(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyDefaultAdvisorAutoProxyCreator.class, MyAdvisor.class, UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.test();
    }

    /**
     * 使用注解开启自动代理
     * 底层通过添加{@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator},
     * 重写了{@link org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator#findCandidateAdvisors()}方法，即可以找到Advisor类型的bean，也能把所有@Aspect注解标注的类扫描出来并生成Advisor
     */
    @Test
    void myEnableAspectJAutoProxy(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyEnableAspectJAutoProxy.class,MyAdvisor.class,UserService.class);
        UserService userService = context.getBean(UserService.class);
        userService.test();
    }

}
