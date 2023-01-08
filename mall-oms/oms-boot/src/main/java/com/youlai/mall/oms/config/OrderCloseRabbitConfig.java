package com.youlai.mall.oms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单超时关单延时队列
 *
 * @author haoxr
 * @date 2022/2/4 23:21
 */

@Component
@Slf4j
public class OrderCloseRabbitConfig {

    // 延迟队列
    private static final String ORDER_CLOSE_DELAY_QUEUE = "order.close.delay.queue";
    private static final String ORDER_EXCHANGE = "order.exchange";
    private static final String ORDER_CLOSE_DELAY_ROUTING_KEY = "order.close.delay.routing.key";

    // 死信队列
    private static final String ORDER_ClOSE_QUEUE = "order.close.queue";
    private static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    private static final String ORDER_ClOSE_ROUTING_KEY = "order.close.routing.key";


    /**
     * 定义交换机
     */
    @Bean
    public Exchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    @Bean
    public Exchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE, true, false);
    }

    /**
     * 延时队列
     */
    @Bean
    public Queue orderDelayQueue() {
        // 延时队列的消息过期了，会自动触发消息的转发，根据routingKey发送到指定的exchange中，exchange路由到死信队列
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", ORDER_ClOSE_ROUTING_KEY); // 死信路由Key
        args.put("x-message-ttl", 5 * 1000L); // 5s
        return new Queue(ORDER_CLOSE_DELAY_QUEUE, true, false, false, args);
    }


    /**
     * 延时队列绑定交换机
     */
    @Bean
    public Binding orderDelayQueueBinding() {
        return new Binding(ORDER_CLOSE_DELAY_QUEUE, Binding.DestinationType.QUEUE, ORDER_EXCHANGE,
                ORDER_CLOSE_DELAY_ROUTING_KEY, null);
    }


    /**
     * 死信队列
     */
    @Bean
    public Queue orderCloseQueue() {
        log.info("死信队列(order.close.queue)创建");
        return new Queue(ORDER_ClOSE_QUEUE, true, false, false);
    }

    /**
     * 死信队列绑定交换机
     */
    @Bean
    public Binding orderCloseQueueBinding() {
        return new Binding(ORDER_ClOSE_QUEUE, Binding.DestinationType.QUEUE, ORDER_DLX_EXCHANGE,
                ORDER_ClOSE_ROUTING_KEY, null);
    }

}
