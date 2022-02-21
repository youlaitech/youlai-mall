package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("订单确认视图层对象")
@Data
public class OrderConfirmVO {

    @ApiModelProperty("订单token")
    private String orderToken;

    @ApiModelProperty("订单明细")
    private List<OrderItemDTO> orderItems;

    @ApiModelProperty("会员收获地址列表")
    private List<MemberAddressDTO> addresses;

}
