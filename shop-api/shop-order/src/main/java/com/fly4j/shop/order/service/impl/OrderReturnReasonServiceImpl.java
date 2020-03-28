package com.fly4j.shop.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.order.pojo.entity.OrderReturnReason;
import com.fly4j.shop.order.mapper.OrderReturnReasonMapper;
import com.fly4j.shop.order.service.IOrderReturnReasonService;
import org.springframework.stereotype.Service;

@Service
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonMapper, OrderReturnReason> implements IOrderReturnReasonService {

}
