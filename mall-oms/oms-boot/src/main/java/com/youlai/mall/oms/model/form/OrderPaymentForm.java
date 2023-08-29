package com.youlai.mall.oms.model.form;

import com.youlai.mall.oms.enums.PaymentMethodEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 订单支付表单对象
 *
 * @author haoxr
 * @since 2.3.0
 */
@Data
@ApiModel("订单支付表单对象")
public class OrderPaymentForm {

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("小程序 AppId")
    String appId;

    @ApiModelProperty("支付方式")
    private PaymentMethodEnum paymentMethodEnum;

}
