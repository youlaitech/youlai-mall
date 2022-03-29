package com.youlai.laboratory.spring.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;

import java.util.List;

/**
 * 根据注解自动创建代理对象
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/29 0029 23:09
 */
public class MyAnnotationAwareAspectJAutoProxyCreator extends AnnotationAwareAspectJAutoProxyCreator {
    public List<Advisor> findEligibleAdvisors(Class clzz,String bean) {
        return super.findEligibleAdvisors(clzz,bean);
    }

    public Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        return super.wrapIfNecessary(bean, beanName, cacheKey);
    }

    //创建代理对象
    @Override
    protected void customizeProxyFactory(ProxyFactory proxyFactory) {
        Advisor[] advisors = proxyFactory.getAdvisors();
        for (Advisor advisor:advisors){

        }
        proxyFactory.getTargetSource();
        super.customizeProxyFactory(proxyFactory);
    }
}
