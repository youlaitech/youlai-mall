package com.youlai.mall.order.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.order.mapper.OrderDeliveryMapper;
import com.youlai.mall.order.model.entity.OmsOrderDelivery;
import com.youlai.mall.order.service.app.OrderDeliveryService;
import org.springframework.stereotype.Service;

@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryMapper, OmsOrderDelivery> implements OrderDeliveryService {

}
