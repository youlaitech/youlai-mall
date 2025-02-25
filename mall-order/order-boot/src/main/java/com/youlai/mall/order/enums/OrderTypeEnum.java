package com.youlai.mall.order.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单类型枚举
 *
 * @author Ray.Hao
 * @since 2024/6/7
 */
@Getter
public enum OrderTypeEnum  implements IBaseEnum<String> {

    WECHAT_ORDER("wxo","微信下单"),
    WECHAT_REFUND("wxr","微信退款"),

    ;
    OrderTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }


    private final String value;

    private final String label;
}
