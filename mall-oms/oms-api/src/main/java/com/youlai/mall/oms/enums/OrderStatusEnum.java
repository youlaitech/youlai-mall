package com.youlai.mall.oms.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@ToString
@AllArgsConstructor
public enum OrderStatusEnum {

    NEED_PAY(101, "待支付"),
    USER_CANCEL(102, "用户取消"),
    SYS_CANCEL(103, "系统自动取消"),
    IS_PAY(201, "已支付"),
    APPLY_REFUND(202, "申请退款"),
    IS_REFUND(203, "已退款"),
    NEED_DELIVER(301, "待发货"),
    IS_DELIVER(402, "已发货"),
    USER_RECEIVE(501, "用户收货"),
    SYS_RECEIVE(502, "系统自动收货"),
    FINISH(901, "已完成"),
    ;
    public final Integer code;

    public final String desc;


}
