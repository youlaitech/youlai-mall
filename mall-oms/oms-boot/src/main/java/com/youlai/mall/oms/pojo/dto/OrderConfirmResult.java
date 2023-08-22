package com.youlai.mall.oms.pojo.dto;

import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("订单确认视图层对象")
@Data
public class OrderConfirmResult {

    @ApiModelProperty("订单防重提交令牌")
    private String orderToken;

    @ApiModelProperty("订单桑普")
    private List<OrderItemDTO> orderItems;

    @ApiModelProperty("会员收获地址列表")
    private List<MemberAddressDTO> addresses;

}
