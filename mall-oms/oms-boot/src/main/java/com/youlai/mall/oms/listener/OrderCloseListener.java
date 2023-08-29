package com.youlai.mall.oms.listener;


import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.service.app.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订单超时未支付系统自动取消监听器
 *
 * @author haoxr
 * @since 2022/12/19
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCloseListener {
    private final OrderService orderService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "order.close.queue")
    public void handleOrderClose(String orderSn, Message message, Channel channel) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息序号（消息队列中的位置）

        log.info("订单({})超时未支付，系统自动关闭订单", orderSn);
        try {
            boolean closeOrderResult = orderService.closeOrder(orderSn);
            log.info("关单结果：{}", closeOrderResult);
            if (closeOrderResult) {
                // 关单成功：释放库存
                try {
                    rabbitTemplate.convertAndSend("stock.exchange", "stock.release", orderSn);
                }catch (Exception e){
                    log.info(e.getMessage());
                }
            } else {
                // 关单失败：订单已被关闭，手动ACK确认并从队列移除消息
                channel.basicAck(deliveryTag, false); // false: 不批量确认，仅确认当前单个消息
            }
        } catch (Exception e) {
            // 关单异常：拒绝消息并重新入队
            try {
                channel.basicReject(deliveryTag, true); //  true: 重新放回队列
                // channel.basicReject(deliveryTag, false); // false: 直接丢弃消息 (TODO 定时任务补偿)
            } catch (IOException ex) {
                log.error("订单({})关闭失败，原因：{}", orderSn, ex.getMessage());
            }

        }
    }
}
