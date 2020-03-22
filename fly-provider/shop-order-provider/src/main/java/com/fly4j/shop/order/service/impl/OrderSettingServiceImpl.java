package com.fly4j.shop.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.order.mapper.OrderSettingMapper;
import com.fly4j.shop.order.pojo.entity.OrderSetting;
import com.fly4j.shop.order.service.IOrderSettingService;
import org.springframework.stereotype.Service;

@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements IOrderSettingService {

}
