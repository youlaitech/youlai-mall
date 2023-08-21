package com.youlai.mall.oms.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单提交结果
 *
 * @author huawei
 * @since 2.0.0
 */
@ApiModel("订单提交结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitResult {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("商户订单号")
    private String tradeNo;

}
