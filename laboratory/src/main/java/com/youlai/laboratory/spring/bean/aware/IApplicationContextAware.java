package com.youlai.laboratory.spring.bean.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *  获取ApplicationContext
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/20 0020 20:36
 */
public class IApplicationContextAware implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("\n--------------------");
        System.out.println("ApplicationContext:"+applicationContext);
        System.out.println("--------------------");
    }
}
