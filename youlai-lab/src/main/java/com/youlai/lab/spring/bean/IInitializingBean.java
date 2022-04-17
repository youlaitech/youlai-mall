package com.youlai.lab.spring.bean;

import org.springframework.beans.factory.InitializingBean;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/20 0020 20:52
 */
public class IInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化bean---InitializingBean");
    }
}
