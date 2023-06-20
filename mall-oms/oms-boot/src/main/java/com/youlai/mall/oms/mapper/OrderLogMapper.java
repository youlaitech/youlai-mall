package com.youlai.mall.oms.mapper;

import com.youlai.mall.oms.model.entity.OmsOrderLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 */
@Mapper
public interface OrderLogMapper extends BaseMapper<OmsOrderLog> {

}
