package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.dao.OrderSettingDao;
import com.youlai.mall.oms.pojo.domain.OmsOrderSetting;
import com.youlai.mall.oms.service.OrderSettingService;
import org.springframework.stereotype.Service;


@Service("orderSettingService")
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingDao, OmsOrderSetting> implements OrderSettingService {

}
