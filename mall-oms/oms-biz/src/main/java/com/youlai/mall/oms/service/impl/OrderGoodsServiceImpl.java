package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.base.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.dao.OrderGoodsDao;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;
import com.youlai.mall.oms.service.OrderGoodsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderGoodsService")
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsDao, OrderGoodsEntity> implements OrderGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderGoodsEntity> page = this.page(
                new Query<OrderGoodsEntity>().getPage(params),
                new QueryWrapper<OrderGoodsEntity>()
        );

        return new PageUtils(page);
    }

}