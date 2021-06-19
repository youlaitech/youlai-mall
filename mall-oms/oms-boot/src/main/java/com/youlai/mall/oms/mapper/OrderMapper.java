package com.youlai.mall.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单详情表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Mapper
public interface OrderMapper extends BaseMapper<OmsOrder> {

    @Select("<script>" +
            " select id,order_sn,total_amount,pay_amount,pay_type,status,total_amount,total_quantity,gmt_create,member_id,source_type from oms_order" +
            " where 1=1 " +
            " <if test ='order.status !=null ' >" +
            "   AND status= #{order.status} " +
            " </if>" +
            " <if test ='order.memberId !=null ' >" +
            "   AND member_id= #{order.memberId} " +
            " </if>" +
            " <if test ='order.orderSn !=null and order.orderSn.trim() neq \"\"' >" +
            "   AND order_sn= #{order.orderSn} " +
            " </if>" +
            " <if test ='order.startDate !=null and order.startDate.trim() neq \"\"' >" +
            "   AND date_format (gmt_crate,'%Y-%m-%d') &gt;= date_format(#{order.startDate},'%Y-%m-%d')" +
            " </if>" +
            " <if test ='order.endDate !=null and order.endDate.trim() neq \"\"' >" +
            "   AND date_format (gmt_crate,'%Y-%m-%d') &lt;= date_format(#{order.endDate},'%Y-%m-%d') " +
            " </if>" +
            " order by gmt_create desc "+
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "orderItems",column = "id",many = @Many(select="com.youlai.mall.oms.mapper.OrderItemMapper.listByOrderId"))
    })
    List<OmsOrder> list(Page<OmsOrder> page, OmsOrder order);
}
