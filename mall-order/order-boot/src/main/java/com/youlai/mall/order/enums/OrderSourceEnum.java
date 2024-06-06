package com.youlai.mall.order.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单来源枚举
 *
 * @author huawei
 * @since 2021/1/16
 */

public enum OrderSourceEnum implements IBaseEnum<Integer> {

    APP(1, "APP"), // APP订单
    WEB(2, "WEB"), // 网页
    ;

    OrderSourceEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }


    @Getter
    private Integer value;

    @Getter
    private String label;
}
