package com.fly4j.yshop.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly4j.yshop.oms.pojo.entity.OmsOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    @Update("<script>" +
            "update oms_order set status=#{status} where order_sn=#{orderToken};" +
            "</script>")
    int updateStatus(@Param("orderToken") String orderToken, @Param("status") Integer status);
}
