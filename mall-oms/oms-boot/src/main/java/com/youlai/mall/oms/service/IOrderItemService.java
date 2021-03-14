package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;

import java.util.List;
import java.util.Map;

/**
 * 订单商品信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface  IOrderItemService extends IService<OmsOrderItem> {

    List<OmsOrderItem> getByOrderId(Long orderId);

    Map<Long,List<OmsOrderItem>> getByOrderIds(List<Long> orderIds);

}

