package com.fly4j.shop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.shop.order.pojo.entity.OrderOperateHistory;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-27 17:10
 **/
public interface OrderOperateHistoryMapper extends BaseMapper<OrderOperateHistory> {

    @Insert("<script>" +
            " INSERT INTO order_operate_history (order_id, operate_man, create_time, order_status, note) VALUES" +
            "        <foreach collection=\"list\" separator=\",\" item=\"item\" index=\"index\">" +
            "            (#{item.orderId}," +
            "            #{item.operateMan}," +
            "            #{item.createTime,jdbcType=TIMESTAMP}," +
            "            #{item.orderStatus}," +
            "            #{item.note})" +
            "        </foreach>" +
            "</script>")
    void insertList(List<OrderOperateHistory> operateHistoryList);
}
