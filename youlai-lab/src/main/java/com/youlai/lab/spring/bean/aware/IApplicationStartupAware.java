package com.youlai.lab.spring.bean.aware;

import org.springframework.context.ApplicationStartupAware;
import org.springframework.core.metrics.ApplicationStartup;

/**
 *  获取ApplicationStartup
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/20 0020 20:35
 */
public class IApplicationStartupAware implements ApplicationStartupAware {

    private ApplicationStartup applicationStartup;
    @Override
    public void setApplicationStartup(ApplicationStartup applicationStartup) {
        this.applicationStartup = applicationStartup;
        System.out.println("\n--------------------");
        System.out.println("ApplicationStartupAware:"+applicationStartup);
        System.out.println("--------------------");
    }
}
