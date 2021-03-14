package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.youlai.mall.oms.dao.OrderItemDao;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.service.OrderGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("orderGoodsService")
@Slf4j
public class OrderGoodsServiceImpl extends ServiceImpl<OrderItemDao, OmsOrderItem> implements OrderGoodsService {


    @Override
    public List<OmsOrderItem> getByOrderId(Long orderId) {
        log.info("根据订单ID，查询订单商品列表，orderId={}", orderId);
        QueryWrapper<OmsOrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("order_id", orderId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<Long, List<OmsOrderItem>> getByOrderIds(List<Long> orderIds) {
        QueryWrapper<OmsOrderItem> orderGoodsQuery = new QueryWrapper<>();
        orderGoodsQuery.in("order_id", orderIds).orderByDesc("order_id", "id");
        List<OmsOrderItem> orderGoods = this.list(orderGoodsQuery);
        if (orderGoods == null || orderGoods.size() == 0) {
            log.info("根据订单ID列表查询商品为空，orderIds={}", orderIds);
            return new HashMap<>(8);
        }
        Map<Long, List<OmsOrderItem>> orderGoodsMap = orderGoods.stream()
                .collect(Collectors.groupingBy(OmsOrderItem::getOrderId));
        return orderGoodsMap;
    }

}
