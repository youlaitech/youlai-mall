package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.base.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.dao.OrderLogsDao;
import com.youlai.mall.oms.pojo.entity.OrderLogsEntity;
import com.youlai.mall.oms.service.OrderLogsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderLogsService")
public class OrderLogsServiceImpl extends ServiceImpl<OrderLogsDao, OrderLogsEntity> implements OrderLogsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderLogsEntity> page = this.page(
                new Query<OrderLogsEntity>().getPage(params),
                new QueryWrapper<OrderLogsEntity>()
        );

        return new PageUtils(page);
    }

}