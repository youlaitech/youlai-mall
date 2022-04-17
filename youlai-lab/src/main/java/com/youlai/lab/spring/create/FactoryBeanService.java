package com.youlai.lab.spring.create;

import com.youlai.lab.spring.Bean;

/**
 * 说明描述
 * 实例工厂方法实例化bean
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/19 0019 0:07
 */
public class FactoryBeanService {
    private static Bean bean = new Bean();
    private FactoryBeanService() {}

    public Bean createInstance() {
        System.out.println("实例工厂方法实例化bean");
        return bean;
    }
}
