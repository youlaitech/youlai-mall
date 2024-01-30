

package com.youlai.auth.authentication.captcha;

/**
 * 验证码模式请求参数名称常量
 *
 * @author haoxr
 * @since 3.0.0
 */
public final class CaptchaParameterNames {

    /**
     * 验证码缓存Key (唯一标识) 用于从Redis获取验证码Code
     */
    public static final String KEY = "captchaKey";


    /**
     * 验证码 Code
     */
    public static final String CODE = "captchaCode";




    private CaptchaParameterNames() {
    }

}
