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
 * 商品库存释放监听器
 *
 * @author haoxr
 * @date 2022/12/20
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class StockReleaseListener {

    private final SkuService skuService;

    private static final String STOCK_RELEASE_QUEUE = "stock.release.queue";
    private static final String STOCK_EXCHANGE = "stock.exchange";
    private static final String STOCK_RELEASE_ROUTING_KEY = "stock.release.routing.key";

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = STOCK_RELEASE_QUEUE, durable = "true"),
            exchange = @Exchange(value = STOCK_EXCHANGE),
            key = {STOCK_RELEASE_ROUTING_KEY}
    ),
            ackMode = "MANUAL" // 手动ACK
    )
    @RabbitHandler
    public void handleStockRelease(String orderSn, Message message, Channel channel) throws IOException {
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
