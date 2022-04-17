package com.youlai.lab.spring.beanDefinition;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/18 0018 20:50
 */
public class BeanC  {


    public void destroy(){
        System.out.println("生命周期回调方法，在bean被销毁时调用");
    }


    public void afterPropertiesSet() {
        System.out.println("生命周期回调方法，在bean完成属性注入后调用");
    }
}
