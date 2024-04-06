package com.youlai.mall.ums.listener;


import com.rabbitmq.client.Channel;
import com.youlai.common.rabbitmq.constant.RabbitMqConstants;
import com.youlai.mall.ums.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartListener {

    private final CartService cartService;


    /**
     * 删除选中购物车商品
     * <p>
     * 订单支付成功时执行
     */
    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = RabbitMqConstants.CART_REMOVE_QUEUE, durable = "true"),
            exchange = @Exchange(value = RabbitMqConstants.CART_EXCHANGE),
            key = {RabbitMqConstants.CART_REMOVE_ROUTING_KEY}
    ),
            ackMode = "MANUAL" // 手动ACK
    )
    @RabbitHandler
    public void removeCheckedCartItems(Long memberId, Message message, Channel channel) throws IOException {
        log.info("用户【{}】删除选中购物车商品", memberId);
        long deliveryTag = message.getMessageProperties().getDeliveryTag(); // 消息序号
        try {
            cartService.removeCheckedCartItems(memberId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, true);
        }
    }

}
