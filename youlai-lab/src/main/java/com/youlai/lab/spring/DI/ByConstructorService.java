package com.youlai.lab.spring.DI;

import com.youlai.lab.spring.Bean;

/**
 * 通过构造器装配
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/18 0018 22:30
 */
public class ByConstructorService {

    private Bean bean;

    public ByConstructorService(Bean bean) {
        this.bean = bean;
    }

    public Bean getBean() {
        return bean;
    }
}
