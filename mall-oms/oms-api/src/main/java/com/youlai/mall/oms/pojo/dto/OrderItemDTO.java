package com.youlai.mall.oms.pojo.dto;

import com.youlai.common.base.BaseVO;
import lombok.*;

/**
 * 订单商品
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO extends BaseVO {

    private Long skuId;
    private Integer count;
    private String pic;
    private String title;
    private Long price;
}
