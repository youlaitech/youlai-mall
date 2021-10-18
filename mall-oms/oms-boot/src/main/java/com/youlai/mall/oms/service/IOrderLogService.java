package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.youlai.mall.oms.pojo.entity.OmsOrderLog;

/**
 * 订单操作历史记录
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface IOrderLogService extends IService<OmsOrderLog> {

    /**
     * 添加订单操作日志记录
     * @param orderId 订单ID
     * @param orderStatus 订单状态
     * @param user 操作人员
     * @param detail 描述信息
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail);

    /**
     * 添加订单操作日志记录
     * @param orderId 订单ID
     * @param orderStatus 订单状态
     * @param detail 描述
     */
    void addOrderLogs(Long orderId, Integer orderStatus, String detail);
}

