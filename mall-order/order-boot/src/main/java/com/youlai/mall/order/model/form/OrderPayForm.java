package com.youlai.mall.order.model.form;

import com.youlai.mall.order.enums.PaymentMethodEnum;
 import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 订单支付表单对象
 *
 * @author Ray.Hao
 * @since 2.3.0
 */
@Data
@Schema(description ="订单支付表单对象")
public class OrderPayForm {

    @Schema(description="订单编号")
    private String orderNo;

    @Schema(description="支付方式")
    private PaymentMethodEnum paymentMethod;

}
