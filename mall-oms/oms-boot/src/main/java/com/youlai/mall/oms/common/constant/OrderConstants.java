package com.youlai.mall.oms.common.constant;

/**
 * 订单常量
 *
 * @author haoxr
 * @since 2.0.0
 */
public interface OrderConstants {

    /**
     * 会员购物车缓存KEY前缀
     */
    String MEMBER_CART_PREFIX = "MEMBER:CART:";

    /**
     * 订单防重提交锁KEY前缀
     */
    String ORDER_PREVENT_DUPLICATE_SUBMIT_LOCK_PREFIX  = "ORDER:PREVENT_DUPLICATE_SUBMIT_LOCK:";


    /**
     * 订单锁前缀
     */
    String ORDER_LOCK_PREFIX = "ORDER:LOCK:";

}
