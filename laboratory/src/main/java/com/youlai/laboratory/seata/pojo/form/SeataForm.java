package com.youlai.laboratory.seata.pojo.form;

import lombok.Data;

/**
 *
 *
 * @author haoxr
 * @date 2022/4/21 23:16
 */
@Data
public class SeataForm {

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 订单金额
     */
    private Long amount;

    /**
     * 是否开启事务
     */
    private boolean openTx;

    /**
     * 是否开启异常
     */
    private boolean openEx;


}
