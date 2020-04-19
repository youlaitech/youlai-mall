package com.fly4j.yshop.oms.listener;

import com.fly4j.yshop.oms.service.IOmsOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Slf4j
public class OrderListener {
    @Resource
    private IOmsOrderService iOmsOrderService;
    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 监听死信队列
     */
    @RabbitListener(queues = {"ORDER-CLOSE-QUEUE"})
    public void closeOrder(String orderToken, Channel channel, Message message) {
        System.out.println(" 订单提交后1分钟到了 你未付款 订单自动关闭");
        try {
            // 关单
            if (this.iOmsOrderService.closeOrder(orderToken) == 1) {
                // 如果关单成功，发送消息给库存系统，释放库存
                this.amqpTemplate.convertAndSend("STOCK-EXCHANGE", "stock.unlock", orderToken);
            }
            // 如果关单失败，说明订单可能已被关闭，直接确认消息
            // 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // 消费失败后重新入队
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e1) {
                log.error("释放库存失败,orderToken=[{}]", orderToken);
            }
        }
    }
}