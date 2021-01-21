package com.youlai.common.redis.constant;

public interface RedisKeyConstants {

    Long REDIS_KEY_TIME_OUT = 3600 * 24L;

    String YOU_LAI = "youlai:";

    String MALL_CART_KEY = YOU_LAI + "cart:";

    String TOKEN_VERIFY ="token_verify:";
}
