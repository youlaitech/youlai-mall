package com.youlai.laboratory.spring.beanDefinition;

import com.google.errorprone.annotations.concurrent.LazyInit;
import com.youlai.laboratory.spring.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * deanDefinition常用属性
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/21 0021 6:56
 */
@Configuration
public class Property {

    @org.springframework.context.annotation.Bean(name = "scope")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)  //单例bean
    public Bean scope(){
        return new Bean();
    }

    @org.springframework.context.annotation.Bean(name = "prototype")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  //原型bean
    public Bean prototype(){
        return new Bean();
    }


    @org.springframework.context.annotation.Bean(name = "lazy")
    @Lazy(value = true)                            //懒加载bean
    public Bean lazy(){
        System.out.println("正在创建bean");
        return new Bean();
    }


}
