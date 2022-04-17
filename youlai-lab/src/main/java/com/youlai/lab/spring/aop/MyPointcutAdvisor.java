package com.youlai.lab.spring.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

/**
 * 自定义的一个切面,包含切点和通知
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/25 0025 23:38
 */
public class MyPointcutAdvisor implements PointcutAdvisor {

    /**
     * 切点
     * @return
     */
    @Override
    public Pointcut getPointcut() {
        NameMatchMethodPointcut methodPointcut = new NameMatchMethodPointcut();
        methodPointcut.addMethodName("test");
        return methodPointcut;
    }

    /**
     * 通知
     * @return
     */
    @Override
    public Advice getAdvice() {
        //前置通知
        MethodBeforeAdvice methodBeforeAdvice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("执行方法前"+method.getName());
            }
        };

        return methodBeforeAdvice;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
