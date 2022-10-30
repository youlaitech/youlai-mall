package com.youlai.laboratory.spring.aop;

import org.springframework.transaction.annotation.Transactional;

/**
 *  测试匹配的切点
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/27 0027 21:23
 */

public class PointcutService {

    public void m1(){
        System.out.println("已匹配包路径:com.youlai.laboratory.spring.aop,类名:PointcutService 方法名:m1,参数无,返回无");
    }

    public String m2(){
        System.out.println("已匹配包路径:com.youlai.laboratory.spring.aop,类名:PointcutService 方法名:m2,参数无,返回String");
        return "OK";
    }

    public void m3(String args3){
        System.out.println("已匹配包路径:com.youlai.laboratory.spring.aop,类名:PointcutService 方法名:m3,参数一个String,返回无");
    }

    @Transactional
    public void m4(){
        System.out.println("已匹配包路径:com.youlai.laboratory.spring.aop,类名:PointcutService 方法名:m4,参数无,返回无,一个@Transactional注解");
    }


}
