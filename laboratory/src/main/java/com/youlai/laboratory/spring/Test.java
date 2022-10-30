package com.youlai.laboratory.spring;

/**
 * @author haoxr
 * @date 2021/11/28 10:06
 */
public interface Test {

    default void test(){
        System.out.println(this);
    };
}
