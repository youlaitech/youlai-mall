package com.youlai.common.constant;

/**
 * OAuth2 常量
 *
 * @author haoxr
 * @since 3.1.0
 */
public interface OAuth2Constants {


    /**
     * 验证码唯一标识, 用于从Redis获取验证码Code和输入的验证码进行比对
     */
    String CAPTCHA_ID = "captchaId";


    /**
     * 验证码 Code
     */
    String CAPTCHA_CODE = "captchaCode";
}
