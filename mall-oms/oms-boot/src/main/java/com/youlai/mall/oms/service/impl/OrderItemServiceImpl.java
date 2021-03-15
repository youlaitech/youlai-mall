package com.youlai.mall.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.dao.OrderItemDao;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.service.IOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OmsOrderItem> implements IOrderItemService {


    @Override
    public List<OmsOrderItem> getByOrderId(Long orderId) {
        log.info("根据订单ID，查询订单商品列表，orderId={}", orderId);
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<OmsOrderItem>().eq(OmsOrderItem::getOrderId, orderId);
        return this.list(queryWrapper);
    }

    @Override
    public Map<Long, List<OmsOrderItem>> getByOrderIds(List<Long> orderIds) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<OmsOrderItem>().in(OmsOrderItem::getOrderId, orderIds)
                .orderByDesc(OmsOrderItem::getOrderId)
                .orderByDesc(OmsOrderItem::getId);

        List<OmsOrderItem> orderItems = this.list(queryWrapper);
        if (CollectionUtil.isEmpty(orderItems)) {
            log.info("根据订单ID列表查询商品为空，orderIds={}", orderIds);
            return new HashMap<>(8);
        }
        Map<Long, List<OmsOrderItem>> orderItemsMap = orderItems.stream()
                .collect(Collectors.groupingBy(OmsOrderItem::getOrderId));
        return orderItemsMap;
    }

}
