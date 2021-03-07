package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.common.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.dao.OrderGoodsDao;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;
import com.youlai.mall.oms.service.OrderGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("orderGoodsService")
@Slf4j
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsDao, OrderGoodsEntity> implements OrderGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderGoodsEntity> page = this.page(
                new Query<OrderGoodsEntity>().getPage(params),
                new QueryWrapper<OrderGoodsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<OrderGoodsEntity> getByOrderId(Long orderId) {
        log.info("根据订单id，查询订单商品列表，orderId={}", orderId);
        QueryWrapper<OrderGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("order_id", orderId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<Long, List<OrderGoodsEntity>> getByOrderIds(List<Long> orderIds) {
        QueryWrapper<OrderGoodsEntity> orderGoodsQuery = new QueryWrapper<>();
        orderGoodsQuery.in("order_id", orderIds).orderByDesc("order_id", "id");
        List<OrderGoodsEntity> orderGoods = this.list(orderGoodsQuery);
        if (orderGoods == null || orderGoods.size() == 0) {
            log.info("根据订单ID列表查询商品为空，orderIds={}", orderIds);
            return new HashMap<>(8);
        }
        Map<Long, List<OrderGoodsEntity>> orderGoodsMap = orderGoods.stream()
                .collect(Collectors.groupingBy(OrderGoodsEntity::getOrderId));
        return orderGoodsMap;
    }

}
