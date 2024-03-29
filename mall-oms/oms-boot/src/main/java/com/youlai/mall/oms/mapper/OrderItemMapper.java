package com.youlai.mall.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.oms.model.entity.OmsOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单商品明细表
 *
 * @author huawei
 * @since 2020-12-30
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OmsOrderItem> {

}
