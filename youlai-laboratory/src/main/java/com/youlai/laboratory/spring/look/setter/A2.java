package com.youlai.laboratory.spring.look.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-09 9:08
 */
@Component
public class A2 {

    private String username = "a";

    private B2 b2;

    public A2() {
        System.out.println("实例化A2:"+this);
    }
    @Autowired
    public void setB(B2 b2) {
        System.out.println("注入属性B2:"+b2);
        this.b2 = b2;
    }


}
