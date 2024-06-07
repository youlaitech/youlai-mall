package com.youlai.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.order.mapper.OrderDeliveryMapper;
import com.youlai.mall.order.model.entity.OmsOrderDelivery;
import com.youlai.mall.order.service.OrderDeliveryService;
import org.springframework.stereotype.Service;

@Service
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryMapper, OmsOrderDelivery> implements OrderDeliveryService {

}
