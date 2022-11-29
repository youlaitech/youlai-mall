package com.youlai.laboratory.seata.service;

import com.youlai.laboratory.seata.pojo.form.SeataForm;
import com.youlai.laboratory.seata.pojo.vo.SeataVO;

/**
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
     * 购买商品
     *
     * @return 订单号
     */
    String purchaseGoods(SeataForm seataForm);

    /**
     * 购买商品(全局事务)
     *
     * @return 订单号
     */
    String purchaseGoodsWithGlobalTx(SeataForm seataForm);
}
