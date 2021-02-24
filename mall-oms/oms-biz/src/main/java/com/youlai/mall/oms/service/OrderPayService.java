package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderPayEntity;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;

import java.util.Map;

/**
 * 支付信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderPayService extends IService<OrderPayEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取订单支付详情
     * @param orderId 订单id
     * @return
     */
    PayInfoVO info(String orderId);

    /**
     * 订单支付
     * @param orderId 订单ID
     */
    void balancePay(String orderId);
}

