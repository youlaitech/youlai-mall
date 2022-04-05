package com.youlai.mall.oms.constant;

/**
 * 订单服务常量
 *
 * @author haoxr
 * @date 2021/03/16
 */
public interface OmsConstants {

    String CART_PREFIX = "cart:";

    String ORDER_TOKEN_PREFIX = "order:token:";

    String ORDER_SN_PREFIX = "order:sn:";

    /**
     * 释放锁lua脚本
     */
    String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


}
