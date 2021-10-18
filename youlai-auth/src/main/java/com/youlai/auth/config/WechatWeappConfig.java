package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信小程序配置
 */
@Configuration
public class WechatWeappConfig {

    @Value("${wechat.weapp.appid}")
    private String appid;

    @Value("${wechat.weapp.secret}")
    private String secret;

    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appid);
        config.setSecret(secret);
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig wxMaConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(wxMaConfig);
        return service;
    }
}
