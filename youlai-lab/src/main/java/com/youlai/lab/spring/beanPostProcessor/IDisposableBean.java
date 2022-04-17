package com.youlai.lab.spring.beanPostProcessor;

import org.springframework.beans.factory.DisposableBean;

/**
 *  销毁bean的回调
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/19 0019 22:50
 */
public class IDisposableBean implements DisposableBean {
    public IDisposableBean() {
        System.out.println("实例化IDisposableBean");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁bean回调");
    }
}
