package com.youlai.mall.oms.common.constant;

/**
 * 订单相关常量
 *
 * <p>该接口定义了与订单相关的常量。</p>
 *
 * @author haoxr
 * @since 2.0.0
 */
public interface OrderConstants {

    /**
     * 会员购物车缓存键前缀
     */
    String MEMBER_CART_PREFIX = "order:cart:";

    /**
     * 订单防重提交令牌缓存键前缀
     */
    String ORDER_TOKEN_KEY_PREFIX = "order:token:";

    /**
     * 订单锁缓存键前缀
     */
    String ORDER_LOCK_KEY_PREFIX = "order:lock";

}
