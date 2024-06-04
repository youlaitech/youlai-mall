package com.youlai.mall.oms.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.mapper.OrderItemMapper;
import com.youlai.mall.oms.model.entity.OmsOrderItem;
import com.youlai.mall.oms.service.app.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OmsOrderItem> implements OrderItemService {


}
