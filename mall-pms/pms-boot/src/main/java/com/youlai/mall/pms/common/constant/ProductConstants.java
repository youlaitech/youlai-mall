package com.youlai.mall.pms.common.constant;

/**
 * 商品模块常量
 *
 * @author haoxr
 * @date 2021/02/28
 */
public interface ProductConstants {

    /**
     * 订单锁定的商品列表key前缀
     */
    String ORDER_LOCKED_SKUS_PREFIX = "order:locked:skus:";

    /**
     * 商品分布式锁key前缀
     */
    String SKU_LOCK_PREFIX = "product:sku:lock:";

    /**
     * 临时规格ID前缀
     */
    String SPEC_TEMP_ID_PREFIX = "tid_";

}
