package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;;
import com.youlai.mall.oms.dao.OrderDeliveryDao;
import com.youlai.mall.oms.pojo.entity.OrderDeliveryEntity;
import com.youlai.mall.oms.service.OrderDeliveryService;
import org.springframework.stereotype.Service;

@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryDao, OrderDeliveryEntity> implements OrderDeliveryService {

}
