package com.youlai.lab.spring.DI;

import com.youlai.lab.spring.Bean;

/**
 *
 * 通过属性类型装配
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/19 0019 17:50
 */
public class ByTypeService {

    public Bean bean;

    @Override
    public String toString() {
        return "ByDefaultService{" +
                "bean=" + bean +
                '}';
    }

    //需要提供set方法，且命名为setXxx
    public void setBean(Bean bean) {
        this.bean = bean;
    }

}
