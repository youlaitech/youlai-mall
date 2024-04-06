package com.youlai.common.rabbitmq.constant;


/**
 * RabbitMQ 常量
 */
public interface RabbitMqConstants {


    String STOCK_EXCHANGE = "stock.exchange";

    String STOCK_RELEASE_QUEUE = "stock.release.queue";

    String STOCK_RELEASE_ROUTING_KEY = "stock.release";


    String ORDER_CLOSE_DELAY_QUEUE = "order.close.delay.queue";
    String ORDER_EXCHANGE = "order.exchange";
    String ORDER_CLOSE_DELAY_ROUTING_KEY = "order.close.delay";


    String ORDER_ClOSE_QUEUE = "order.close.queue";
    String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    String ORDER_ClOSE_ROUTING_KEY = "order.close";


    String CART_EXCHANGE = "cart.exchange";
    String CART_REMOVE_QUEUE ="cart.checked-items.remove.queue";
    String CART_REMOVE_ROUTING_KEY = "cart.checked-items.remove";

}
