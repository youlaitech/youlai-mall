package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.common.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.dao.OrderLogsDao;
import com.youlai.mall.oms.pojo.entity.OrderLogsEntity;
import com.youlai.mall.oms.service.OrderLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderLogsService")
@Slf4j
public class OrderLogsServiceImpl extends ServiceImpl<OrderLogsDao, OrderLogsEntity> implements OrderLogsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderLogsEntity> page = this.page(
                new Query<OrderLogsEntity>().getPage(params),
                new QueryWrapper<OrderLogsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail) {
        try {
            log.info("添加订单操作日志，orderId={}，detail={}", orderId, detail);
            OrderLogsEntity orderLogs = new OrderLogsEntity();
            orderLogs.setDetail(detail);
            orderLogs.setOrderId(orderId);
            orderLogs.setOrderStatus(orderStatus);
            orderLogs.setUser(user);
            baseMapper.insert(orderLogs);
        } catch (Exception e) {
            log.error("添加订单操作日志失败，orderId={}", orderId, e.getMessage());
        }
    }

    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String detail) {
        Long userId = RequestUtils.getUserId();
        addOrderLogs(orderId, orderStatus, userId.toString(), detail);
    }

}
