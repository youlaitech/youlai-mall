package com.youlai.mall.order.mapper;

import com.youlai.mall.order.model.entity.OrderRefund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.order.model.bo.OrderRefundBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单退款记录 Mapper 接口
 *
 * @author Ray.Hao
 * @since 2024-06-07
 */

@Mapper
public interface OrderRefundMapper extends BaseMapper<OrderRefund> {


}
