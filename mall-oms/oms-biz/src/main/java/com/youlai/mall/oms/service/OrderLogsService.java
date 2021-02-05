package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderLogsEntity;

import java.util.Map;

/**
 * 订单操作历史记录
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderLogsService extends IService<OrderLogsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加订单操作日志记录
     * @param orderId
     * @param orderStatus
     * @param user
     * @param detail
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail);
}

