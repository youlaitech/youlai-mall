package com.youlai.lab.spring.DI;

import com.youlai.lab.spring.Bean;

/**
 *
 * 演示默认装配（no）
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/19 0019 17:50
 */
public class ByDefaultService {

    public Bean bean;

    @Override
    public String toString() {
        return "ByDefaultService{" +
                "bean=" + bean +
                '}';
    }


}
