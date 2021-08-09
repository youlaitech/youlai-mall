package com.youlai.mall.oms.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付配置
 *
 * @author Gadfly
 */
@Slf4j
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
@AllArgsConstructor
public class WxPayConfiguration {
    private final WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        if (StringUtils.isBlank(properties.getMchId())) {
            log.error("微信商户号配置无效，请检查！");
            return new WxPayServiceImpl();
        }

        WxPayService wxPayService = new WxPayServiceImpl();
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setMchId(StringUtils.trimToNull(properties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(properties.getMchKey()));
        payConfig.setSubAppId(StringUtils.trimToNull(properties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(properties.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(properties.getKeyPath()));
        payConfig.setApiV3Key(StringUtils.trimToNull(properties.getApiV3Key()));
        payConfig.setCertSerialNo(StringUtils.trimToNull(properties.getCertSerialNo()));
        payConfig.setPrivateKeyPath(StringUtils.trimToNull(properties.getPrivateKeyPath()));
        payConfig.setPrivateCertPath(StringUtils.trimToNull(properties.getPrivateCertPath()));
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(BooleanUtils.toBoolean(properties.getSandboxEnabled()));
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

}
