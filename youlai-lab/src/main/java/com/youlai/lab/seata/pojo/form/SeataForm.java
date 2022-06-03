package com.youlai.lab.seata.pojo.form;

import lombok.Data;

/**
 * @author haoxr
 * @date 2022/4/21 23:16
 */
@Data
public class SeataForm {


    /**
     * 是否开启事务
     */
    private boolean openTx;

    /**
     * 订单异常
     */
    private boolean orderEx;


}
