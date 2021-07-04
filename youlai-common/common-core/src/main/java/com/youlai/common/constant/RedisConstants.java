package com.youlai.common.constant;

public interface RedisConstants {

    String BUSINESS_NO_PREFIX = "business_no:";

    /**
     * 优惠券码KEY前缀
     */
    String SMS_COUPON_TEMPLATE_CODE_KEY = "sms_coupon_template_code_";

    /**
     * 用户当前所有可用优惠券key
     */
    String SMS_USER_COUPON_USABLE_KEY = "sms_user_coupon_usable_";

    /**
     * 用户当前所有已使用优惠券key
     */
    String SMS_USER_COUPON_USED_KEY = "sms_user_coupon_used_";

    /**
     * 用户当前所有已过期优惠券key
     */
    String SMS_USER_COUPON_EXPIRED_KEY = "sms_user_coupon_expired_";



}
