package com.youlai.lab.spring.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Aspect切面
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/29 0029 21:59
 */
@EnableAspectJAutoProxy
@Aspect
public class AspectJAop {

    @Before("execution(* com.youlai.lab.spring.aop.UserService.test(..))")
    public void before(){
        System.out.println("前置通知");
    }


    public void test(){
        System.out.println("执行正常业务");
    }

    @After("execution(* com.youlai.lab.spring.aop.UserService.test(..))")
    public void after(){
        System.out.println("后置增强,不管正常或异常都会执行");
    }

//    @Around("execution(* com.youlai.laboratory.spring.aop.AspectJAop.test(..))")
//    public void around(){
//        System.out.println("环绕增强");
//    }

    @AfterThrowing("execution(* com.youlai.lab.spring.aop.UserService.test(..))")
    public void afterthrows(){
        System.out.println("异常抛出增强");
    }

    @AfterReturning("execution(* com.youlai.lab.spring.aop.UserService.test(..))")
    public void afterReturning(){
        System.out.println("正常退出的后置增强");
    }

}
