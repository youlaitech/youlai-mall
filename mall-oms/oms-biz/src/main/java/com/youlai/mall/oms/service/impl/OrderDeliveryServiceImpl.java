package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.base.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.dao.OrderDeliveryDao;
import com.youlai.mall.oms.pojo.entity.OrderDeliveryEntity;
import com.youlai.mall.oms.service.OrderDeliveryService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryDao, OrderDeliveryEntity> implements OrderDeliveryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderDeliveryEntity> page = this.page(
                new Query<OrderDeliveryEntity>().getPage(params),
                new QueryWrapper<OrderDeliveryEntity>()
        );

        return new PageUtils(page);
    }

}