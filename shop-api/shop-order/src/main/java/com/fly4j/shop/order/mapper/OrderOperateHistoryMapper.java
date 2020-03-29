package com.fly4j.shop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.order.pojo.entity.OrderOperateHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-27 17:10
 **/
public interface OrderOperateHistoryMapper extends BaseMapper<OrderOperateHistory> {


    @Select("select * from order_operate_history where order_id =#{id}")
    List<OrderOperateHistory> selectListByOrderId(@Param("id") Long id);

}
