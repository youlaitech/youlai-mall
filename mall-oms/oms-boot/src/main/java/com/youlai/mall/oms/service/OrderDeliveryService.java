package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderDeliveryEntity;

import java.util.Map;

/**
 * 订单物流记录表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderDeliveryService extends IService<OrderDeliveryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

