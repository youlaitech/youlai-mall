package com.youlai.common.constant;

public interface SecurityConstants {

    /**
     * 黑名单TOKEN Key前缀
     */
    String BLACKLIST_TOKEN_PREFIX = "AUTH:BLACKLIST_TOKEN:";

    /**
     * 验证码key前缀
     */
    String VERIFY_CODE_CACHE_KEY_PREFIX = "AUTH:VERIFY_CODE:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "AUTH:SMS_CODE:";


    /**
     * 用户权限集合缓存前缀
     */
    String USER_PERMS_CACHE_KEY_PREFIX = "AUTH:USER_PERMS:";

    /**
     * 授权信息中的权限或角色的key
     */
    String AUTHORITIES_CLAIM_NAME_KEY = "authorities";


    /**
     * 商城管理端客户端ID
     */
    String MALL_ADMIN_CLIENT_ID = "mall-admin";

    /**
     * 商城移动端客户端ID
     */
    String MALL_APP_CLIENT_ID = "mall-app";

    /**
     * Knife4j测试客户端ID（Knife4j自动填充的 access_token 须原生返回，不能被包装成业务码数据格式）
     */
    String KNIFE4J_TEST_CLIENT_ID = "client";

}
