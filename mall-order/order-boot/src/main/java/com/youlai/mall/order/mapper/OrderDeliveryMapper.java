package com.youlai.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.order.model.entity.OmsOrderDelivery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单物流记录表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Mapper
public interface OrderDeliveryMapper extends BaseMapper<OmsOrderDelivery> {

}
