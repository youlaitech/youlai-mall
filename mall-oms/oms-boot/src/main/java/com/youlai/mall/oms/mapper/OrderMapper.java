package com.youlai.mall.oms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
            " select id,order_sn,total_amount,pay_amount,status from oms_order" +
            " where 1=1 " +
            " <if test ='order.status !=null ' >" +
            "   AND status= #{order.status} " +
            " </if>" +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "orderItems",column = "id",many = @Many(select="com.youlai.mall.oms.mapper.OrderItemMapper.listByOrderId"))
    })
    List<OmsOrder> list(Page<OmsOrder> page, OmsOrder order);
}
