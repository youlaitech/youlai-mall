package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.ums.pojo.entity.UmsAddress;
import lombok.Data;

import java.util.List;


@Data
public class OrderConfirmVO {

    private String orderToken;

    private List<OrderItemDTO> orderItems;

    private List<UmsAddress> addresses;

}
