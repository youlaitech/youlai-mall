package com.fly4j.yshop.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.yshop.oms.pojo.entity.OmsOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    @Update("update oms_order set status= #{status} where order_sn= #{orderToken}" )
    int updateStatus(@Param("orderToken") String orderToken, @Param("status") Integer status);

    @Update("update oms_order set status = 4 WHERE order_sn=#{orderToken} AND status = 0")
    int closeOrder(@Param("orderToken") String orderToken);
}
