package com.youlai.mall.oms.model.form;

import com.youlai.mall.oms.enums.PaymentMethodEnum;
 import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 订单支付表单对象
 *
 * @author haoxr
 * @since 2.3.0
 */
@Data
@Schema(description ="订单支付表单对象")
public class OrderPaymentForm {

    @Schema(description="订单编号")
    private String orderSn;

    @Schema(description="小程序 AppId")
    String appId;

    @Schema(description="支付方式")
    private PaymentMethodEnum paymentMethod;

}
