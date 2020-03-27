package com.fly4j.shop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.order.pojo.dto.OrderDeliveryDTO;
import com.fly4j.shop.order.pojo.dto.OrderDetailDTO;
import com.fly4j.shop.order.pojo.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 批量发货
     */
    @Update("<script>" +
            "UPDATE order_info SET delivery_sn = CASE id" +
            "        <foreach collection=\"list\" item=\"item\">" +
            "            WHEN #{item.orderId} THEN #{item.deliverySn}" +
            "        </foreach>" +
            "        END," +
            "        delivery_company = CASE id" +
            "        <foreach collection=\"list\" item=\"item\">" +
            "            WHEN #{item.orderId} THEN #{item.deliveryCompany}" +
            "        </foreach>" +
            "        END," +
            "        delivery_time = CASE id" +
            "        <foreach collection=\"list\" item=\"item\">" +
            "            WHEN #{item.orderId} THEN now()" +
            "        </foreach>" +
            "        END," +
            "        `status` = CASE id" +
            "        <foreach collection=\"list\" item=\"item\">" +
            "            WHEN #{item.orderId} THEN 2" +
            "        </foreach>" +
            "        END" +
            "        WHERE" +
            "        id IN" +
            "        <foreach collection=\"list\" item=\"item\" separator=\",\" open=\"(\" close=\")\">" +
            "            #{item.orderId}\n" +
            "        </foreach>" +
            "        AND `status` = 1" +
            "</script>")
    boolean delivery(@Param("list") List<OrderDeliveryDTO> deliveryParamList);

    /**
     * 获取订单详情
     */
    @Select("<script>" +
            " SELECT o.*," +
            "            oi.id item_id," +
            "            oi.goods_id item_goods_id," +
            "            oi.goods_sn item_goods_sn," +
            "            oi.goods_pic item_goods_pic," +
            "            oi.goods_name item_goods_name," +
            "            oi.goods_brand item_goods_brand," +
            "            oi.goods_price item_goods_price," +
            "            oi.goods_quantity item_goods_quantity," +
            "            oi.goods_attr item_goods_attr," +
            "            oh.id history_id," +
            "            oh.operate_man history_operate_man," +
            "            oh.create_time history_create_time," +
            "            oh.order_status history_order_status," +
            "            oh.note history_note" +
            "        FROM" +
            "            order_info o" +
            "            LEFT JOIN order_item oi ON o.id = oi.order_id" +
            "            LEFT JOIN order_operate_history oh ON o.id = oh.order_id" +
            "        WHERE" +
            "            o.id = #{id}" +
            "        ORDER BY oi.id ASC,oh.create_time DESC" +
            "</script>")
    OrderDetailDTO getDetail(@Param("id") Long id);
}
