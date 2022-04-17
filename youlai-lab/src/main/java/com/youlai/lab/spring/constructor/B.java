package com.youlai.lab.spring.constructor;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-15 11:05
 */
public class B {

    private A a;
    public B() {
        System.out.println("B无参构造函数");
    }

    @Autowired
    public B(A a) {
        System.out.println("autowired指定的构造函数");
        this.a = a;
    }
}
