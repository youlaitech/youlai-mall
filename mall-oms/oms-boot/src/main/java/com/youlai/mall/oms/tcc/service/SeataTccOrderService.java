package com.youlai.mall.oms.tcc.service;

import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface SeataTccOrderService {

    @TwoPhaseBusinessAction(name = "prepareSubmitOrder", commitMethod = "commitSubmitOrder", rollbackMethod = "rollbackSubmitOrder")
    OmsOrder prepareSubmitOrder(BusinessActionContext businessActionContext,
                                @BusinessActionContextParameter(paramName = "orderSubmitDTO") OrderSubmitDTO orderSubmitDTO);

    boolean commitSubmitOrder(BusinessActionContext businessActionContext);

    boolean rollbackSubmitOrder(BusinessActionContext businessActionContext);
}
