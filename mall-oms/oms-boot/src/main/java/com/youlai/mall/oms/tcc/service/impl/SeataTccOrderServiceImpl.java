package com.youlai.mall.oms.tcc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.OrderTypeEnum;
import com.youlai.mall.oms.mapper.OrderMapper;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.entity.OmsOrderItem;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.tcc.idempotent.IdempotentUtil;
import com.youlai.mall.oms.tcc.service.SeataTccOrderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SeataTccOrderServiceImpl implements SeataTccOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IOrderItemService orderItemService;

    @Transactional
    @Override
    public OmsOrder prepareSubmitOrder(BusinessActionContext businessActionContext, OrderSubmitDTO orderSubmitDTO) {
        log.info("==========创建 订单 第一阶段，事务组Xid:{} ==========", businessActionContext.getXid());
        List<OrderItemDTO> orderItems = orderSubmitDTO.getOrderItems();
        String orderToken = orderSubmitDTO.getOrderToken();
        // 创建订单(状态：待支付)
        OmsOrder order = new OmsOrder();
        order.setOrderSn(orderToken)
                .setStatus(OrderStatusEnum.PENDING_PAYMENT.getCode())
                .setSourceType(OrderTypeEnum.APP.getCode())
                .setMemberId(JwtUtils.getUserId())
                .setRemark(orderSubmitDTO.getRemark())
                .setPayAmount(orderSubmitDTO.getPayAmount())
                .setTotalQuantity(orderItems.stream().map(item -> item.getCount()).reduce(0, (x, y) -> x + y))
                .setTotalAmount(orderItems.stream().map(item -> item.getPrice() * item.getCount()).reduce(0l, (x, y) -> x + y))
                .setGmtCreate(new Date());
        orderMapper.insert(order);
        int i = 1 / 0;
        // 创建订单商品
        List<OmsOrderItem> orderItemList = orderItems.stream().map(item -> OmsOrderItem.builder()
                .orderId(order.getId())
                .skuId(item.getSkuId())
                .skuName(item.getSkuName())
                .skuPrice(item.getPrice())
                .skuPic(item.getPic())
                .skuQuantity(item.getCount())
                .skuTotalPrice(item.getCount() * item.getPrice())
                .skuCode(item.getSkuCode())
                .build()).collect(Collectors.toList());
        orderItemService.saveBatch(orderItemList);
        log.info("保存订单：{} 成功", order.getOrderSn());
        IdempotentUtil.addMarker(getClass(), businessActionContext.getXid(), System.currentTimeMillis());
        return order;
    }


    @Override
    @Transactional
    public boolean commitSubmitOrder(BusinessActionContext businessActionContext) {
        if (Objects.isNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {

            return true;
        }
        IdempotentUtil.removeMarker(getClass(), businessActionContext.getXid());
        return true;
    }

    @Override
    @Transactional
    public boolean rollbackSubmitOrder(BusinessActionContext businessActionContext) {
        if (Objects.isNull(IdempotentUtil.getMarker(getClass(), businessActionContext.getXid()))) {
            return true;
        }
        JSONObject jsonObject = (JSONObject) businessActionContext.getActionContext("orderSubmitDTO");
        OrderSubmitDTO orderSubmitDTO = new OrderSubmitDTO();
        BeanUtil.copyProperties(jsonObject, orderSubmitDTO);
        OmsOrder omsOrder = orderMapper.selectOne(new LambdaQueryWrapper<OmsOrder>().eq(OmsOrder::getOrderSn, orderSubmitDTO.getOrderToken()));
        if (Objects.nonNull(omsOrder)) {
            orderItemService.remove(new LambdaQueryWrapper<OmsOrderItem>().eq(OmsOrderItem::getOrderId, omsOrder.getId()));
            orderMapper.deleteById(omsOrder.getId());
        }
        IdempotentUtil.removeMarker(getClass(), businessActionContext.getXid());
        return true;
    }
}
