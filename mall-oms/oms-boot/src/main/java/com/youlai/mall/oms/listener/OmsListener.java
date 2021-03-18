package com.youlai.mall.oms.listener;

import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.app.PmsSkuFeignService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author hxr
 * @date 2021-03-16
 */

@Component
@AllArgsConstructor
@Slf4j
public class OmsListener {

    IOrderService orderService;
    IOrderItemService orderItemService;
    PmsSkuFeignService skuFeignService;
    RabbitTemplate rabbitTemplate;

    /**
     * 订单超时未支付，关闭订单，释放库存
     */
    @RabbitListener(queues = "order.close.queue")
    public void closeOrder(String orderToken, Message message, Channel channel) {
        try {
            if (orderService.closeOrder(orderToken)) {
                // 如果关单成功，发送消息释放库存
                skuFeignService.unlockStock(orderToken);
            } else {
                // 如果关单失败，则订单可能已经被处理，直接手动ACK确认消息
                // basicAck(tag,multiple)，multiple为true开启批量确认，小于tag值队列中未被消费的消息一次性确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            }
        } catch (IOException e) {
            // 消费失败后，重新入队
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ioException) {
                log.error("系统关单失败");
            }
        }
    }


}
