package com.youlai.mall.oms.pojo.dto;

import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.entity.OmsOrderItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDTO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

}
