package com.youlai.mall.oms.config.rabbitmq;

/**
 * @author huawei
 * @desc RabbitMQ 相关常量
 * @email huawei_code@163.com
 * @date 2021/1/17
 */
public class RabbitMqConstants {

    public static final String ORDER_EVENT_EXCHANGE = "order_event_exchange";

    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    public static final String ORDER_RELEASE_QUEUE = "order.release.queue";

    public static final String ORDER_CREATE_ORDER_KEY = "order.create.order";

    public static final String ORDER_RELEASE_ORDER_KEY = "order.release.order";

    public static final Integer ORDER_RELEASE_TTL = 60000;
}
