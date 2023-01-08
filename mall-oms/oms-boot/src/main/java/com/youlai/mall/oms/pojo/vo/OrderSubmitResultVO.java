package com.youlai.mall.oms.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单提交结果
 *
 * @author huawei
 * @date 2021/1/21
 */
@ApiModel("订单提交结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitResultVO {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号，进入支付页面显示")
    private String orderSn;

}
