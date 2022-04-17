package com.youlai.lab.spring;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/11/28 10:06
 */
public interface Test {

    default void test(){
        System.out.println(this);
    };
}
