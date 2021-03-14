package com.youlai.mall.oms.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author huawei
 * @desc 订单支付表单
 * @email huawei_code@163.com
 * @date 2021/2/21
 */
@Data
public class OrderPayForm implements Serializable {

    @NotNull(message = "支付类型不能为空")
    private Integer payType;

    @NotBlank(message = "订单ID不能为空")
    private Long orderId;
}
