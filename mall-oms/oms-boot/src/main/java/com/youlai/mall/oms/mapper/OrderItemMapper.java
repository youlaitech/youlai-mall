package com.youlai.mall.oms.mapper;

import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单商品信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OmsOrderItem> {

}
