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
public enum PayTypeEnum  {

    WEIXIN(1,"微信支付"),
    ALIPAY(2,"支付宝支付"),
    BALANCE(3,"会员余额支付")
    ;
    @Getter
    private Integer code;

    @Getter
    private String text;

    public static PayTypeEnum getByCode(Integer code){
        for (PayTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
