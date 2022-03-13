package com.youlai.mall.oms.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 订单分页视图对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/1 20:58
 */
@Data
public class OrderPageVO {

    private Long id;

    private String orderSn;

    private Long totalAmount;

    private Long payAmount;

    private Integer payType;

    private Integer status;

    private Integer totalQuantity;

    private Date gmtCreate;

    private Long memberId;

    private Integer sourceType;

    private List<OrderItem> orderItems;

    @Data
    public static class OrderItem {

        private Long id;

        private Long orderId;

        private Long skuId;

        private String skuName;

        private String picUrl;

        private Long price;

        private Integer count;

        private Long totalAmount;

        private String spuName;

    }

}
