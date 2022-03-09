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
public class B3 {
    private String username = "b";

    private A3 a3;

    public B3() {
        System.out.println("实例化B2:"+this);
    }

    @Autowired
    public void setA(A3 a3) {
        System.out.println("注入属性A2:"+ a3);
        this.a3 = a3;
    }


}
