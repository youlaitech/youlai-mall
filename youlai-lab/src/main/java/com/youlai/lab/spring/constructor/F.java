package com.youlai.lab.spring.constructor;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-15 15:08
 */
public class F {



    @Autowired(required = false)
    public F(H h) {
        System.out.println("h构造器");
    }

    @Autowired(required = false)
    public F(G g){
        System.out.println("g构造器");
    }

    @Autowired(required = false)
    public F(I i){
        System.out.println("i构造器");
    }

    @Autowired(required = false)
    public F(J j){
        System.out.println("j构造器");
    }


}
