package com.youlai.mall.oms.listener;


import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 订单超时未支付取消
 *
 * @author haoxr
 * @date 2022/12/19
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCloseListener {
    private final OrderService orderService;

    // 普通队列(创建订单，超时未支付转发至关单(死信)队列)
    private static final String ORDER_CREATE_QUEUE = "order.create.queue";
    private static final String ORDER_EXCHANGE = "order.exchange";
    private static final String ORDER_CREATE_ROUTING_KEY = "order.create.routing.key";

    // 死信队列(关单队列)
    private static final String ORDER_ClOSE_QUEUE = "order.close.queue";
    private static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    private static final String ORDER_ClOSE_ROUTING_KEY = "order.close.routing.key";

    /**
     * 普通队列(创建订单)消费处理
     * <p>
     * 超过 x-message-ttl 设定时间未被消费会转发到死信队列 ORDER_ClOSE_QUEUE
     */
    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = ORDER_CREATE_QUEUE,
                    arguments =
                            {
                                    @Argument(name = "x-dead-letter-exchange", value = ORDER_DLX_EXCHANGE),
                                    @Argument(name = "x-dead-letter-routing-key", value = ORDER_ClOSE_ROUTING_KEY),
                                    @Argument(name = "x-message-ttl", value = "10000", type = "java.lang.Long") // 单位毫秒
                            }),
            exchange = @Exchange(value = ORDER_EXCHANGE),
            key = {ORDER_CREATE_ROUTING_KEY}
    )
    )
    @RabbitHandler
    public void handleOrderCreate(Message message, Channel channel) throws Exception {
        /**
         * @param 参数1
         * @param multiple 是否批量处理（true:批量拒绝所有小于deliveryTag的消息；false:只处理当前消息）
         * @param requeue 拒绝是否重新入队列 （true:消息重新入队；false:禁止消息重新入队）
         */
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicNack(deliveryTag, false, false);  // 等于 channel.basicReject(deliveryTag, false);

    }

    /**
     * 死信队列(关单)消费处理
     */
    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = ORDER_ClOSE_QUEUE, durable = "true"),
            exchange = @Exchange(value = ORDER_DLX_EXCHANGE),
            key = {ORDER_ClOSE_ROUTING_KEY}
    ),
            ackMode = "MANUAL" // 手动ACK
    )
    @RabbitHandler
    public void handleOrderClose(String orderSn, Message message, Channel channel) throws IOException {

        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息序号

        log.info("订单 【{}】 超时未支付，系统自动关闭订单", orderSn);
        try {
            orderService.closeOrder(orderSn);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, true);
        }
    }
}
