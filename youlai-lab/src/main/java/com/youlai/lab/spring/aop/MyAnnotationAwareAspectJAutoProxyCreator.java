package com.youlai.lab.spring.aop;

import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据注解自动创建代理对象
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/29 0029 23:09
 */
public class MyAnnotationAwareAspectJAutoProxyCreator extends AnnotationAwareAspectJAutoProxyCreator {
    ArrayList<Advisor> advisors = new ArrayList<>();
    @SneakyThrows
    public List<Advisor> findEligibleAdvisors(Class clzz, String bean) {
        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(clzz.newInstance());

        for(Method method:clzz.getMethods()){
            if(method.isAnnotationPresent(Before.class)){
                //解析切点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                AspectJMethodBeforeAdvice beforeAdvice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
                //切面
                DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
                advisors.add(pointcutAdvisor);
            }else if(method.isAnnotationPresent(AfterReturning.class)){
                //解析切点
                String expression = method.getAnnotation(AfterReturning.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                AspectJAfterReturningAdvice beforeAdvice = new AspectJAfterReturningAdvice(method, pointcut, factory);
                //切面
                DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
                advisors.add(pointcutAdvisor);
            }else if(method.isAnnotationPresent(After.class)){
                //解析切点
                String expression = method.getAnnotation(After.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                AspectJAfterAdvice beforeAdvice = new AspectJAfterAdvice(method, pointcut, factory);
                //切面
                DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
                advisors.add(pointcutAdvisor);
            }
        }
        return advisors;
//        return super.findEligibleAdvisors(clzz,bean);
    }

    //判断目标类是否需要创建代理,如果是则返回代理对象
    public Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        return super.wrapIfNecessary(bean, beanName, cacheKey);
    }




      // 环绕通知调用链
      public static class MyReflectiveMethodInvocation extends ReflectiveMethodInvocation{
        private Object target;
        private Method method;
        private Object[] args;
        List<Object> methodInterceptorList;
        private int count = 1;
        /**
         * Construct a new ReflectiveMethodInvocation with the given arguments.
         *
         * @param proxy                                the proxy object that the invocation was made on
         * @param target                               the target object to invoke
         * @param method                               the method to invoke
         * @param arguments                            the arguments to invoke the method with
         * @param targetClass                          the target class, for MethodMatcher invocations
         * @param interceptorsAndDynamicMethodMatchers interceptors that should be applied,
         *                                             along with any InterceptorAndDynamicMethodMatchers that need evaluation at runtime.
         *                                             MethodMatchers included in this struct must already have been found to have matched
         *                                             as far as was possibly statically. Passing an array might be about 10% faster,
         */
        protected MyReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
            super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
            this.target=target;
            this.method = method;
            this.args = arguments;
            this.methodInterceptorList = interceptorsAndDynamicMethodMatchers;
        }


          @Override
          public Object proceed() throws Throwable {
            if(count>methodInterceptorList.size()){  //递归退出条件
               return method.invoke(target,args);
            }
            //逐一调用通知
              MethodInterceptor methodInterceptor = (MethodInterceptor)methodInterceptorList.get(count++ - 1);
              return methodInterceptor.invoke(this); //递归
          }
      }

    public static MyReflectiveMethodInvocation createReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers){
        return new MyReflectiveMethodInvocation(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
    }
}
