package com.youlai.laboratory.spring.DI;

import com.youlai.laboratory.spring.Bean;

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
