package com.fly4j.yshop.oms.pojo.dto;


import com.fly4j.yshop.oms.pojo.entity.OmsOrder;
import com.fly4j.yshop.oms.pojo.entity.OmsOrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OrderDTO {

    @ApiModelProperty(name = "订单基本信息")
    private OmsOrder order;

    @ApiModelProperty(name = "订单商品明细信息")
    private List<OmsOrderItem> order_item_list;

}
