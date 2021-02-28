package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.oms.bo.OrderBO;
import com.youlai.mall.oms.pojo.OmsOrder;

public interface IOmsOrderService extends IService<OmsOrder> {

    boolean save(OrderBO orderBO);

    OrderBO getByOrderId(Long id);

    boolean submit();

    boolean submitWithGlobalTransactional();
}
