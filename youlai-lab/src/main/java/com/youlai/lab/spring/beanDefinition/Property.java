package com.youlai.lab.spring.beanDefinition;

import com.youlai.lab.spring.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * deanDefinition常用属性,参考 {@link org.springframework.beans.factory.config.BeanDefinition}
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/21 0021 6:56
 */
@Configuration
@Import(value=ImportBean.class)
public class Property {

    /**
     *  默认注册的bean
     * {@link org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod(BeanMethod beanMethod)}
     * @return {@link Bean}
     */
    @org.springframework.context.annotation.Bean
    public Bean default1(){
       return new Bean();
    }


    /**
     * spring默认为单例bean,既scope==singleton
     *  {@link org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod(BeanMethod beanMethod)}
     * @return {@link Bean}
     */

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


    /**
     * spring默认为非懒加载的bean,既Lazy=false
     *@return 懒加载bean
     */
    @org.springframework.context.annotation.Bean(name = "lazy")
    @Lazy
    public Bean lazy(){
        System.out.println("正在创建bean");
        return new Bean();
    }


    @org.springframework.context.annotation.Bean
    @Primary                       //优先使用的bean
    public Bean primary(){
        Bean bean = new Bean();
        bean.setName("primary");
        return bean;
    }

    /**
     * spring先加载beanB再加载自己
     * @return {@link Bean}
     */
    @DependsOn("beanB")
    @org.springframework.context.annotation.Bean(name="depends")
    public Bean depends(){
        System.out.println("正在创建depends");
        return new Bean();
    }

    @org.springframework.context.annotation.Bean(name="beanB")
    public BeanB beanb(){
        System.out.println("正在创建beanB");
        return new BeanB();
    }


    /**
     * 条件注入bean
     * @return
     */
    @org.springframework.context.annotation.Bean(name="conditional")
    @Conditional(DevConditional.class)
    public Bean bean(){
        return new Bean();
    }



    
    /**
     * 条件注入bean
     * @return
     */
    @org.springframework.context.annotation.Bean(name="profile")
    @Profile("dev")
    public Bean profile(){
        return new Bean();
    }




}
