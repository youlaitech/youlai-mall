package com.youlai.lab.spring.constructor;

import java.util.function.Supplier;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022-03-15 11:13
 */
public class C implements Supplier<C> {
    @Override
    public C get() {
        System.out.println("Supplier");
        return new C();
    }
}
