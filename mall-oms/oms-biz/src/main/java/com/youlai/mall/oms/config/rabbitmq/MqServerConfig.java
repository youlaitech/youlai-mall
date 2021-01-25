//package com.youlai.mall.oms.config.rabbitmq;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.Exchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.youlai.mall.oms.config.rabbitmq.RabbitMqConstants.*;
//
//
///**
// * @author huawei
// * @desc rabbitmq 业务相关配置类
// * 订单相关业务统一使用 order-event-exchange 交换机
// * order.delay.queue 队列，用于存放需要延时多长时间处理的订单，不需要被消费
// * order.release.queue 队列，用于真正执行处理订单，需要被业务消费，如果订单还没有付款需要自动关闭订单并释放库存
// * <p>
// * order.create.order 建立交换机与 order.delay.queue 队列的绑定关系，目的是将新创建的订单放入延时队列中
// * order.release.order 建立交换机与 order.release.queue 队列的绑定关系，目的是将延时队列中到达延时时间的订单执行释放操作
// * <p>
// * 自动创建相关 交换机，队列，路由键
// * @email huawei_code@163.com
// * @date 2021/1/17
// */
//@Configuration
//public class MqServerConfig {
//
//    /**
//     * order.delay.queue 队列
//     * String name, boolean durable, boolean exclusive,
//     * boolean autoDelete, @Nullable Map<String, Object> arguments
//     *
//     * @return
//     */
//    @Bean
//    public Queue orderDelayQueue() {
//        Map<String, Object> arguments = new HashMap<>();
//        // 设置交换机
//        arguments.put("x-dead-letter-exchange", ORDER_EVENT_EXCHANGE);
//        // 设置过期路由键
//        arguments.put("x-dead-letter-routing-key", ORDER_RELEASE_ORDER_KEY);
//        // 设置过期时间
//        arguments.put("x-message-ttl", ORDER_RELEASE_TTL);
//        return new Queue(ORDER_DELAY_QUEUE, true, false, false, arguments);
//    }
//
//    @Bean
//    public Queue orderReleaseQueue() {
//        return new Queue(ORDER_RELEASE_QUEUE, true, false, false);
//    }
//
//    @Bean
//    public Exchange orderEventExchange() {
//        return new TopicExchange(ORDER_EVENT_EXCHANGE, true, false);
//    }
//
//    @Bean
//    public Binding orderCreateBinding() {
//        return new Binding(ORDER_DELAY_QUEUE,
//                Binding.DestinationType.QUEUE,
//                ORDER_EVENT_EXCHANGE, ORDER_CREATE_ORDER_KEY,
//                null);
//    }
//
//    @Bean
//    public Binding orderReleaseBinding() {
//        return new Binding(ORDER_RELEASE_QUEUE,
//                Binding.DestinationType.QUEUE,
//                ORDER_EVENT_EXCHANGE, ORDER_RELEASE_ORDER_KEY,
//                null);
//    }
//
//}
