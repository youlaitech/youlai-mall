package com.youlai.mall.order.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付配置属性
 *
 * @author Gadfly
 * @see com.github.binarywang.wxpay.config.WxPayConfig
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {

    /**
     * 微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 商户私钥文件 apiclient_key.pem的绝对路径或者以classpath:开头的类路径
     */
    private String privateKeyPath;

    /**
     * 商户公钥文件 apiclient_cert.pem的绝对路径或者以classpath:开头的类路径
     */
    private String privateCertPath;

    /**
     * apiV3 秘钥值.
     */
    private String apiV3Key;

    /**
     * 微信支付异步回掉地址
     */
    private String notifyUrl;

    /**
     * 是否为沙盒环境
     */
    private Boolean sandboxEnabled;


}
