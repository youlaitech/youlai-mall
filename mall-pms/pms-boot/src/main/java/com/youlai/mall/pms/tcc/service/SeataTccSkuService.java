package com.youlai.mall.pms.tcc.service;

import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.util.List;

@LocalTCC
public interface SeataTccSkuService {

    @TwoPhaseBusinessAction(name = "prepareSkuLockList", commitMethod = "commitSkuLockList", rollbackMethod = "rollbackSkuLockList")
    boolean prepareSkuLockList(BusinessActionContext businessActionContext,
                                @BusinessActionContextParameter(paramName = "skuLockList") List<LockStockDTO> skuLockList);

    boolean commitSkuLockList(BusinessActionContext businessActionContext);

    boolean rollbackSkuLockList(BusinessActionContext businessActionContext);
}
