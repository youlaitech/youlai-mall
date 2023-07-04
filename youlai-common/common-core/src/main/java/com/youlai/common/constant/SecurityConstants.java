package com.youlai.common.constant;

public interface SecurityConstants {

    /**
     * 黑名单TOKEN Key前缀
     */
    String BLACKLIST_TOKEN_PREFIX = "AUTH:BLACKLIST_TOKEN:";

    /**
     * 验证码key前缀
     */
    String VERIFY_CODE_KEY_PREFIX = "AUTH:VERIFY_CODE:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "AUTH:SMS_CODE:";

    /**
     * 系统管理 web 客户端ID
     */
    String ADMIN_CLIENT_ID = "mall-admin";

    /**
     * 移动端（H5/Android/IOS）客户端ID
     */
    String APP_CLIENT_ID = "mall-app";

    /**
     * 用户权限集合缓存前缀
     */
    String USER_PERMS_CACHE_PREFIX = "AUTH:USER_PERMS:";

    /**
     * 授权信息中的权限或角色的key
     */
    String AUTHORITIES_CLAIM_NAME_KEY="authorities";

}
