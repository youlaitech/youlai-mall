package com.fly4j.shop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.order.pojo.dto.OrderDeliveryDTO;
import com.fly4j.shop.order.pojo.dto.OrderDetailDTO;
import com.fly4j.shop.order.pojo.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {


    @Update("<script>" +
            "UPDATE order_info SET delivery_no = CASE id" +
            "        <foreach collection=\"list\" item=\"item\">" +
            "            WHEN #{item.orderId} THEN #{item.deliveryNo}" +
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
    boolean deliver(@Param("list") List<OrderDeliveryDTO> deliveryParamList);


    @Select(" SELECT * " +
            " FROM order_info " +
            " WHERE id = #{id} " +
            " ORDER by create_time DESC"
    )
    @Results({
            @Result(property = "orderItemList",column = "id",
                    many = @Many(select = "com.fly4j.shop.order.mapper.OrderItemMapper.selectListByOrderId")),
            @Result(property = "historyList",column = "id",
                    many = @Many(select = "com.fly4j.shop.order.mapper.OrderOperateHistoryMapper.selectListByOrderId"))
    })
    OrderDetailDTO getDetail(@Param("id") Long id);

}
