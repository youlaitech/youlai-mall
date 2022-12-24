package com.youlai.mall.oms.common.constant;

/**
 * 订单常量
 *
 * @author haoxr
 * @date 2021/03/16
 */
public interface OmsConstants {

    /**
     * 会员购物车缓存KEY前缀
     */
    String MEMBER_CART_PREFIX = "MEMBER:CART:";

    /**
     * 订单防重提交锁KEY前缀
     */
    String ORDER_RESUBMIT_LOCK_PREFIX = "ORDER:RESUBMIT_LOCK:";


    /**
     * 订单锁前缀
     *
     */
    String ORDER_LOCK_PREFIX = "ORDER:LOCK:";

}
