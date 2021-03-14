package com.youlai.mall.oms.common;

public interface RedisConstants {

    Long REDIS_KEY_TIME_OUT = 3600 * 24L;

    String CART_KEY = "cart:";

    String TOKEN_VERIFY = "token_verify:";

    String BUSINESS_NO_PREFIX = "businessno:";
}
