package com.youlai.mall.oms.listener;

import com.rabbitmq.client.Channel;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.app.PmsSkuFeignService;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hxr
 * @date 2021-03-16
 */

@Component
@AllArgsConstructor
@Slf4j
public class RabbitMQListener {

    IOrderService orderService;

    IOrderItemService orderItemService;

    PmsSkuFeignService inventoryFeignService;

    RabbitTemplate rabbitTemplate;


    /**
     * 订单超时未支付，关闭订单，释放库存
     */
    @RabbitListener(queues = "order.close.queue")
    public void closeOrder(Long orderId, Message message, Channel channel) {
        try {
            if (orderService.closeOrder(orderId)) {
                // 如果关单成功，发送消息释放库存
                // rabbitTemplate.convertAndSend("product_event_change", "inventory:unlock", orderSn);
                List<OmsOrderItem> orderItems = orderItemService.getByOrderId(orderId);
                List<InventoryDTO> inventoryList = orderItems.stream().map(orderItem -> InventoryDTO.builder()
                        .skuId(orderItem.getSkuId())
                        .count(orderItem.getSkuQuantity())
                        .build())
                        .collect(Collectors.toList());
                inventoryFeignService.unlockInventory(inventoryList);
            } else {
                // 如果关单失败，则订单可能已经被处理，直接手动ACK确认消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            }
        } catch (IOException e) {
            // 消费失败后，重新入队
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException ioException) {
                log.error("释放库存失败,orderId=[{}]", orderId);
            }
        }
    }


}
