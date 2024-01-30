package com.youlai.common.constant;

public interface SecurityConstants {

    /**
     * 黑名单TOKEN Key前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    /**
     * 图形验证码key前缀
     */
    String CAPTCHA_CODE_PREFIX = "captcha_code:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "sms_code:";

    /**
     * Knife4j测试客户端ID（Knife4j自动填充的 access_token 须原生返回，不能被包装成业务码数据格式）
     */
    String TEST_OAUTH2_CLIENT_ID = "client";

    String JWK_SET_KEY = "jwk_set";

    /**
     * 角色和权限缓存前缀
     */
    String ROLE_PERMS_PREFIX = "role_perms:";

}
