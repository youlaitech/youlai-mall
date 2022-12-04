package com.youlai.laboratory.seata.service;

import com.youlai.laboratory.seata.pojo.form.SeataForm;
import com.youlai.laboratory.seata.pojo.vo.SeataVO;

/**
 * Seata 实验室接口层
 *
 * @author haoxr
 * @date 2022/4/16 20:49
 */
public interface SeataService {

    /**
     * 获取模拟数据
     *
     * @return
     */
    SeataVO getData();

    /**
     * 重置模拟数据
     *
     * @return
     */
    boolean resetData();

    /**
     * 订单支付
     */
    boolean payOrder(SeataForm seataForm);

    /**
     * 订单支付(全局事务)
     */
    boolean payOrderWithGlobalTx(SeataForm seataForm);
}
