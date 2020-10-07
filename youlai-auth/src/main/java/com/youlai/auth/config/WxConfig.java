package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {
    @Autowired
    private WxProperties properties;


/*
    @Bean
    public cn.binarywang.wx.miniapp.config.WxMaConfig wxMaConfig() {
        WxMaConfig config = new WxMaConfig();
        config.setAppid(properties.getAppId());
        config.setSecret(properties.getAppSecret());
        return config;
    }


    @Bean
    public WxMaService wxMaService(cn.binarywang.wx.miniapp.config.WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }
*/


}
