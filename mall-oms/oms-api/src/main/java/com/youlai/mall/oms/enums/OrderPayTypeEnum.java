package com.youlai.mall.oms.enums;

import com.youlai.common.base.BaseCodeEnum;
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
public enum OrderPayTypeEnum implements BaseCodeEnum {

    WEIXIN(1, "微信支付"),
    ALIPAY(2, "支付宝支付"),
    BALANCE(3, "会员余额支付"),
    ;
    public final Integer code;

    public final String desc;


    @Override
    public Integer getCode() {
        return code;
    }
}
