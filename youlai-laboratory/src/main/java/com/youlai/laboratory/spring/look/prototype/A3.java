package com.youlai.laboratory.spring.look.prototype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-09 9:08
 */
@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class A3  {

    private String username = "a";

    private B3 b3;

    public A3() {
        System.out.println("实例化A2:"+this);
    }
    @Autowired
    public void setB(B3 b3) {
        System.out.println("注入属性B2:"+ b3);
        this.b3 = b3;
    }



}
