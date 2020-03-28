package com.fly4j.shop.order.pojo.dto;

import com.fly4j.shop.order.pojo.entity.Order;
import com.fly4j.shop.order.pojo.entity.OrderItem;
import com.fly4j.shop.order.pojo.entity.OrderOperateHistory;
import lombok.Data;

import java.util.List;

/**
 * @description: 订单详情信息
 * @author: Mr.
 * @create: 2020-03-27 16:35
 **/
@Data
public class OrderDetailDTO extends Order {

    private List<OrderItem> orderItemList;
    private List<OrderOperateHistory> historyList;
}
