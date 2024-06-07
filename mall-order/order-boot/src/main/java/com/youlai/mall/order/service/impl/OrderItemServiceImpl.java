package com.youlai.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.order.mapper.OrderItemMapper;
import com.youlai.mall.order.model.entity.OmsOrderItem;
import com.youlai.mall.order.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OmsOrderItem> implements OrderItemService {


}
