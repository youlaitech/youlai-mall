package com.youlai.mall.oms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbitmq.client.Channel;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.oms.config.rabbitmq.OmsRabbitConstants;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.OrderRabbitService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.app.InventoryFeignService;
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

    private IOrderService orderService;
    private IOrderItemService orderItemService;
    private InventoryFeignService inventoryFeignService;

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
            OmsOrder order = orderService.getOne(new LambdaQueryWrapper<OmsOrder>()
                    .eq(OmsOrder::getOrderSn, orderSn));
            if (order.getStatus().equals(OrderStatusEnum.PENDING_PAYMENT.getCode())) { // 待支付订单超时未支付系统自动取消
                if (orderService.autoCancelOrder(orderSn)) {
                    List<OmsOrderItem> orderItems = orderItemService.getByOrderId(order.getId());
                    List<InventoryDTO> items = orderItems.stream().map(item -> InventoryDTO.builder()
                            .skuId(item.getSkuId())
                            .num(item.getSkuQuantity()).build())
                            .collect(Collectors.toList());
                    Result result = inventoryFeignService.unlockInventory(items);
                    if (!StrUtil.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
                        throw new BizException("关闭订单失败，释放库存错误");
                    }
                }
            }
            channel.basicAck(msgTag, false);
        } catch (Exception e) {
            throw new BizException("关闭订单失败，orderSn=" + orderSn);
        }
    }
}
