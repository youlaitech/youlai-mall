package com.youlai.lab.spring.bean.aware;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * 获取当前环境
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/20 0020 20:29
 */

public class IEnvironmentAware implements EnvironmentAware {

    private Environment environment;
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        System.out.println("\n--------------------");
        System.out.println("当前环境:"+environment);
        System.out.println("--------------------");
    }
}
