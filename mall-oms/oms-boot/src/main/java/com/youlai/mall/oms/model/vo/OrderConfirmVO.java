package com.youlai.mall.oms.model.vo;

import com.youlai.mall.oms.model.dto.OrderItemDTO;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "订单确认视图层对象")
@Data
public class OrderConfirmVO {

    @Schema(description="订单token")
    private String orderToken;

    @Schema(description="订单明细")
    private List<OrderItemDTO> orderItems;

    @Schema(description="会员收获地址列表")
    private List<MemberAddressDTO> addresses;

}
