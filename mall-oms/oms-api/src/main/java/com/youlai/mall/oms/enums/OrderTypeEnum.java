package com.youlai.mall.oms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huawei
 * @desc 订单来源类型枚举
 * @email huawei_code@163.com
 * @date 2021/1/16
 */

@AllArgsConstructor
public enum OrderTypeEnum  {

    WEB(0), // PC订单
    APP(1), // APP订单
    ;

    @Getter
    private Integer code;

    public static OrderTypeEnum getValue(Integer code){
        for (OrderTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
