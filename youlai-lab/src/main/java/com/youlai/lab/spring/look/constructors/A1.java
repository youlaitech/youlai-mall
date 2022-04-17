package com.youlai.lab.spring.look.constructors;

import org.springframework.stereotype.Component;

/**
 * 构造器注入的bean
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-09 10:28
 */
//@Component
public class A1 {

    private B1 b1;

    public A1(B1 b1) {
        this.b1 = b1;
    }
}
