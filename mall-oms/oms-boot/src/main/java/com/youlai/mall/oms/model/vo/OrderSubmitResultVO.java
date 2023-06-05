package com.youlai.mall.oms.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单提交结果
 *
 * @author huawei
 * @since 2021/1/21
 */
@Schema(description = "订单提交结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitResultVO {

    @Schema(description="订单ID")
    private Long orderId;

    @Schema(description="订单编号，进入支付页面显示")
    private String orderSn;

}
