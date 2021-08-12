package com.youlai.mall.oms.enums;


import lombok.Getter;

/**
 * @author huawei
 * @desc 订单来源类型枚举
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
public enum PayTypeEnum {

    WEIXIN_JSAPI(1,"微信JSAPI支付"),
    ALIPAY(2,"支付宝支付"),
    BALANCE(3,"会员余额支付"),
    WEIXIN_APP(4,"微信APP支付");

    PayTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @Getter
    private Integer code;

    @Getter
    private String text;

    public static PayTypeEnum getByCode(Integer code) {
        PayTypeEnum payTypeEnum = null;

        for (PayTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                payTypeEnum = value;
            }
        }
        return payTypeEnum;
    }

}
