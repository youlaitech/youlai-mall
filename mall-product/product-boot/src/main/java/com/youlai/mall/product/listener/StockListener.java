package com.youlai.mall.product.listener;

import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.constant.RabbitMqConstants;
import com.youlai.mall.product.service.SkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 商品库存释放监听器
 *
 * @author Ray
 * @since 2022/12/20
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StockListener {

    private final SkuService skuService;


    /**
     * 订单取消释放库存
     */
    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = RabbitMqConstants.STOCK_RELEASE_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitMqConstants.STOCK_EXCHANGE),
            key = {RabbitMqConstants.STOCK_RELEASE_ROUTING_KEY}
    ),
            ackMode = "MANUAL" // 手动ACK
    )
    @RabbitHandler
    public void releaseStock(String orderSn, Message message, Channel channel) throws IOException {
        log.info("订单【{}】取消释放库存消息监听", orderSn);
        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息序号
        try {
            skuService.unlockStock(orderSn);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, true);
        }
    }
}
