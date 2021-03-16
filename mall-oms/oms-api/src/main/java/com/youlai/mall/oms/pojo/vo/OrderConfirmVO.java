package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单确认页需要的数据
 */

public class OrderConfirmVO extends BaseVO {

    /**
     * 订单总额
     */
    @Setter
    private Long totalPrice;


    /**
     * 订单商品
     */
    @Getter
    @Setter
    private List<OrderItemVO> items;


    public Long getTotalPrice() {
        Long total = 0L;
        if (items != null && items.size() > 0) {
            total = items.stream().mapToLong(OrderItemVO::getSubtotal).sum();
        }
        return total;
    }
}
