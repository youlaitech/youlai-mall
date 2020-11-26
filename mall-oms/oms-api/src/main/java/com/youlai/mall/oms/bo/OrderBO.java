package com.youlai.mall.oms.bo;


import com.youlai.mall.oms.pojo.OmsOrder;
import com.youlai.mall.oms.pojo.OmsOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderBO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

}
