package com.youlai.mall.oms.common.enums;


import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单支付类型枚举
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
public enum PayTypeEnum implements IBaseEnum<Integer> {

    WX_JSAPI(1, "微信JSAPI支付"),
    ALIPAY(2, "支付宝支付"),
    BALANCE(3, "会员余额支付"),
    WX_APP(4, "微信APP支付");

    PayTypeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private Integer value;

    @Getter
    private String label;
}
