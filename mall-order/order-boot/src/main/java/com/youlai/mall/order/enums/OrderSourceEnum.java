package com.youlai.mall.order.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单来源枚举
 *
 * @author huawei
 * @since 2021/1/16
 */

@Getter
public enum OrderSourceEnum implements IBaseEnum<Integer> {

    APP(1, "APP"),
    WEB(2, "WEB")
    ;

    OrderSourceEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }


    private final Integer value;

    private final String label;
}
