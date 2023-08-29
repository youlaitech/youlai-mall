package com.youlai.mall.pms.listener;

import com.rabbitmq.client.Channel;
import com.youlai.mall.pms.service.SkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 商品库存
 *
 * @author haoxr
 * @since 2022/12/20
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StockReleaseListener {

    private final SkuService skuService;

    private static final String STOCK_RELEASE_QUEUE = "stock.release.queue";
    private static final String STOCK_EXCHANGE = "stock.exchange";
    private static final String STOCK_RELEASE_ROUTING_KEY = "stock.release";

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = STOCK_RELEASE_QUEUE, durable = "true"),
            exchange = @Exchange(value = STOCK_EXCHANGE),
            key = {STOCK_RELEASE_ROUTING_KEY}
    ),
            ackMode = "MANUAL" // 手动ACK
    )
    @RabbitHandler
    public void handleStockRelease(String orderSn, Message message, Channel channel) {
        log.info("订单({})取消释放库存", orderSn);
        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息序号
        try {
            skuService.unlockStock(orderSn);
            // 手动ACK确认消息并从队列移除消息
            channel.basicAck(deliveryTag, false); // false: 不批量确认，仅确认当前单个消息
        } catch (Exception e) {
            // 库存释放异常：拒绝消息并重新入队
            try {
                channel.basicReject(deliveryTag, true); // true: 重新放回队列
            } catch (IOException ex) {
                log.error("订单({})关闭失败，原因：{}", orderSn, ex.getMessage());
            }
        }
    }
}
