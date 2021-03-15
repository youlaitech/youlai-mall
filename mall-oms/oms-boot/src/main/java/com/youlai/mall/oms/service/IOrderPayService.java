package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.youlai.mall.oms.pojo.domain.OmsOrderPay;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import org.springframework.stereotype.Service;

/**
 * 支付信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */

public interface IOrderPayService extends IService<OmsOrderPay> {

    boolean payWithBalance(Long orderId);

    PayInfoVO getByOrderId(Long orderId);
}

