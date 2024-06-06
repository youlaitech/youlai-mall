package com.youlai.mall.order.config;

import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.youlai.mall.order.config.property.WxPayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付自动装配类
 *
 * @author Gadfly
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
@RequiredArgsConstructor
@Slf4j
public class WxPayConfiguration {

    private final WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        if (StringUtils.isBlank(properties.getMchId())) {
            log.error("微信商户号配置无效，请检查！");
            throw new IllegalArgumentException("微信商户号配置无效");
        }

        WxPayService wxPayService = new WxPayServiceImpl();
        com.github.binarywang.wxpay.config.WxPayConfig payConfig = new com.github.binarywang.wxpay.config.WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(properties.getAppId())); // 小程序或公众号appid
        payConfig.setMchId(StringUtils.trimToNull(properties.getMchId())); // 商户号
        payConfig.setMchKey(StringUtils.trimToNull(properties.getMchKey())); // 商户密钥
        payConfig.setApiV3Key(StringUtils.trimToNull(properties.getApiV3Key())); // 微信支付apiV3密钥
        payConfig.setPrivateKeyPath(StringUtils.trimToNull(properties.getPrivateKeyPath())); // 微信支付私钥路径
        payConfig.setPrivateCertPath(StringUtils.trimToNull(properties.getPrivateCertPath())); // 微信支付证书路径
        payConfig.setNotifyUrl(StringUtils.trimToNull(properties.getNotifyUrl())); // 微信支付异步通知地址
        payConfig.setUseSandboxEnv(BooleanUtils.toBoolean(properties.getSandboxEnabled()));  // 是否使用沙箱环境

        log.debug("WxPayConfig created: {}", payConfig);

        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

}
