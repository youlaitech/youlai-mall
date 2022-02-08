package com.youlai.mall.oms.pojo.vo;

import lombok.Data;

/**
 * 订单创建响应视图对象
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
@Data
public class OrderSubmitVO {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号，进入支付页面显示
     */
    private String orderSn;

}
