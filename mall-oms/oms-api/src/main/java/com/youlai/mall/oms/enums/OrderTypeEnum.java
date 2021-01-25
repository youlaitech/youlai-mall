package com.youlai.mall.oms.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author huawei
 * @desc 订单来源类型枚举
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@ToString
@AllArgsConstructor
public enum OrderTypeEnum {

    WEB(0, "PC订单"),
    APP(1, "APP订单"),
    ;
    public final Integer code;

    public final String desc;


}
