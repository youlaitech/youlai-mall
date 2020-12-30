package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.base.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.dao.OrderPayDao;
import com.youlai.mall.oms.pojo.entity.OrderPayEntity;
import com.youlai.mall.oms.service.OrderPayService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderPayService")
public class OrderPayServiceImpl extends ServiceImpl<OrderPayDao, OrderPayEntity> implements OrderPayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderPayEntity> page = this.page(
                new Query<OrderPayEntity>().getPage(params),
                new QueryWrapper<OrderPayEntity>()
        );

        return new PageUtils(page);
    }

}