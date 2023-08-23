package com.youlai.mall.oms.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.mapper.OrderSettingMapper;
import com.youlai.mall.oms.pojo.entity.OmsOrderSetting;
import com.youlai.mall.oms.service.app.OrderSettingService;
import org.springframework.stereotype.Service;


@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OmsOrderSetting> implements OrderSettingService {

}
