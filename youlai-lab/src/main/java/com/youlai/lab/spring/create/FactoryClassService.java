package com.youlai.lab.spring.create;

import com.youlai.lab.spring.Bean;

/**
 *
 * 静态工厂实例化bean
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/18 0018 23:37
 */
public class FactoryClassService {
    private static Bean bean = new Bean();
    private FactoryClassService() {}
    public static Bean createInstance() {
        System.out.println("静态工厂方法实例化bean");
        return bean;
    }
}
