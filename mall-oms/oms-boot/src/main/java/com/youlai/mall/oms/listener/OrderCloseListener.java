package com.youlai.mall.oms.listener;


import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;

import java.io.IOException;

/**
 * 订单超时未支付取消
 *
 * @author haoxr
 * @date 2022/12/19
 */
//@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCloseListener {
    private final OrderService orderService;

    // 延迟队列
    private static final String ORDER_CLOSE_DELAY_QUEUE = "order.close.delay.queue";
    private static final String ORDER_EXCHANGE = "order.exchange";
    private static final String ORDER_CLOSE_DELAY_ROUTING_KEY = "order.close.delay.routing.key";

    // 关单队列
    private static final String ORDER_ClOSE_QUEUE = "order.close.queue";
    private static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
    private static final String ORDER_ClOSE_ROUTING_KEY = "order.close.routing.key";

    /**
     * 延迟队列
     * <p>
     * 超过 x-message-ttl 设定时间未被消费转发到死信交换机
     */
   @RabbitListener(bindings =
            {
                    @QueueBinding(
                            value = @Queue(value = ORDER_CLOSE_DELAY_QUEUE,
                                    arguments =
                                            {
                                                    @Argument(name = "x-dead-letter-exchange", value = ORDER_DLX_EXCHANGE),
                                                    @Argument(name = "x-dead-letter-routing-key", value = ORDER_ClOSE_ROUTING_KEY),
                                                    @Argument(name = "x-message-ttl", value = "5000", type = "java.lang.Long") // 超时10s
                                            }),
                            exchange = @Exchange(value = ORDER_EXCHANGE),
                            key = {ORDER_CLOSE_DELAY_ROUTING_KEY}
                    )
            }, ackMode = "MANUAL" // 手动ACK
    )
    public void handleOrderCloseDelay(String orderSn, Message message, Channel channel) throws IOException {
        log.info("订单【{}】延时队列，10s内如果未支付将路由到关单队列", orderSn);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        /**
         * @param deliveryTag 消息序号
         * @param multiple 是否批量处理（true:批量拒绝所有小于deliveryTag的消息；false:只处理当前消息）
         * @param requeue 拒绝是否重新入队列 （true:消息重新入队；false:禁止消息重新入队）
         */
        //channel.basicReject(deliveryTag, false);  // 等于 channel.basicReject(deliveryTag, false);
    }

    /**
     * 关单队列
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = ORDER_ClOSE_QUEUE, durable = "true"),
                    exchange = @Exchange(value = ORDER_DLX_EXCHANGE),
                    key = {ORDER_ClOSE_ROUTING_KEY}
            )
    }, ackMode = "MANUAL" // 手动ACK
    )
    @RabbitListener(queues = "order.close.queue")
    public void handleOrderClose(String orderSn, Message message, Channel channel) throws IOException {

        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息序号

        log.info("订单 【{}】 超时未支付，系统自动关闭订单", orderSn);
        try {
            orderService.closeOrder(orderSn);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

            // TODO 关单失败，入定时任务表
            channel.basicReject(deliveryTag, false);
        }
    }
}
