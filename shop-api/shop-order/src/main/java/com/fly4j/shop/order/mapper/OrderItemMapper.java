package com.fly4j.shop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.order.pojo.entity.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Select("select * from order_item where order_id =#{id}")
    List<OrderItem> selectListByOrderId(@Param("id") Long id);
}
