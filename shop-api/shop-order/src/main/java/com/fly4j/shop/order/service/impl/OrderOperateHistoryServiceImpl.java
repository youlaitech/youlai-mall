package com.fly4j.shop.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.order.pojo.entity.OrderOperateHistory;
import com.fly4j.shop.order.mapper.OrderOperateHistoryMapper;
import com.fly4j.shop.order.service.IOrderOperateHistoryService;
import org.springframework.stereotype.Service;

@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements IOrderOperateHistoryService {

}
