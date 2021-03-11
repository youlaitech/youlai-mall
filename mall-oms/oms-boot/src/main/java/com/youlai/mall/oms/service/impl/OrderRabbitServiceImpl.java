package com.youlai.mall.oms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.Channel;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.config.rabbitmq.OmsRabbitConstants;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;
import com.youlai.mall.oms.service.OrderGoodsService;
import com.youlai.mall.oms.service.OrderRabbitService;
import com.youlai.mall.oms.service.OrderService;
import com.youlai.mall.pms.api.SkuFeignService;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RabbitListener(queues = OmsRabbitConstants.ORDER_RELEASE_QUEUE)
@AllArgsConstructor
@Slf4j
public class OrderRabbitServiceImpl implements OrderRabbitService {

    private OrderService orderService;

    private OrderGoodsService orderGoodsService;

    private SkuFeignService skuFeignService;

    /**
     * 接收超时订单消息
     * 订单已支付 - 确认接收消息
     * 订单未支付 - 关闭订单，释放库存
     * 出现异常，消费消息失败，将数据重新放入队列，等待下次消费
     *
     * @param orderSn
     */
    @Override
    @RabbitHandler
    @GlobalTransactional(rollbackFor = Exception.class)
    public void releaseOrder(String orderSn, Message message, Channel channel) {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        log.info("获取到消息，msgTag={}，message={}，body={}", msgTag, message.toString(), orderSn);

        try {
            OrderEntity order = orderService.getByOrderSn(orderSn);
            if (order.getStatus().equals(OrderStatusEnum.NEED_PAY.code)) {
                if (orderService.closeOrderBySystem(orderSn)){
                    unlockInventory(order.getId());
                }
            }
            channel.basicAck(msgTag, false);
        } catch (Exception e) {
            log.error("关闭订单失败，orderSn={}", orderSn);
            throw new BizException("关闭订单失败，orderSn=" + orderSn);
        }
    }

    private void unlockInventory(Long orderId) {
        List<OrderGoodsEntity> orderGoods = orderGoodsService.getByOrderId(orderId);
        List<InventoryDTO> items = orderGoods.stream().map(good -> {
            InventoryDTO item = new InventoryDTO();
            item.setInventoryId(good.getSkuId());
            item.setNum(good.getSkuQuantity());
            return item;
        }).collect(Collectors.toList());
        Result result = skuFeignService.unlockInventory(items);
        if (result == null || !StrUtil.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
            log.error("释放库存异常，商品列表={}", items);
            throw new BizException("关闭订单失败，释放库存错误");
        }
    }
}
