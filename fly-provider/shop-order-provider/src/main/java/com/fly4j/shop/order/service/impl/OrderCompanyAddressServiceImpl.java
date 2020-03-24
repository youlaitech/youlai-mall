package com.fly4j.shop.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.order.mapper.OrderCompanyAddressMapper;
import com.fly4j.shop.order.mapper.OrderSettingMapper;
import com.fly4j.shop.order.pojo.entity.OrderCompanyAddress;
import com.fly4j.shop.order.pojo.entity.OrderSetting;
import com.fly4j.shop.order.service.IOrderCompanyAddressService;
import com.fly4j.shop.order.service.IOrderSettingService;
import org.springframework.stereotype.Service;

@Service
public class OrderCompanyAddressServiceImpl extends ServiceImpl<OrderCompanyAddressMapper, OrderCompanyAddress> implements IOrderCompanyAddressService {

}
