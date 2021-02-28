package com.youlai.mall.oms.dao;

import com.youlai.mall.oms.pojo.entity.OrderLogsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Mapper
public interface OrderLogsDao extends BaseMapper<OrderLogsEntity> {
	
}
