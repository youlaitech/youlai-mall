package com.youlai.mall.order.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.order.mapper.OrderSettingMapper;
import com.youlai.mall.order.model.entity.OmsOrderSetting;
import com.youlai.mall.order.service.app.OrderSettingService;
import org.springframework.stereotype.Service;


@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OmsOrderSetting> implements OrderSettingService {

}
