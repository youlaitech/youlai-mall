package com.youlai.mall.order.model.dto;

import com.youlai.mall.order.model.entity.OmsOrder;
import com.youlai.mall.order.model.entity.OmsOrderItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDTO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

}
