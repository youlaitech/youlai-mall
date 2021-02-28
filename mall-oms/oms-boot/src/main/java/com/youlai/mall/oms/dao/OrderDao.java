package com.youlai.mall.oms.dao;

import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情表
 * 
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
