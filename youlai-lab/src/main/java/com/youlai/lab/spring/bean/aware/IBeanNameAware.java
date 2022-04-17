package com.youlai.lab.spring.bean.aware;

import org.springframework.beans.factory.BeanNameAware;

/**
 *  获取bean的名字
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/20 0020 19:25
 */
public class IBeanNameAware implements BeanNameAware {

    private String name;

    @Override
    public void setBeanName(String name) {
        System.out.println("bean的名字:"+name);
        this.name = name;
    }
}
