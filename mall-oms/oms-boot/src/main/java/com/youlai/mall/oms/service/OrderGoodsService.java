package com.youlai.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单商品信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
public interface OrderGoodsService extends IService<OrderGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<OrderGoodsEntity> getByOrderId(Long orderId);

    Map<Long,List<OrderGoodsEntity>> getByOrderIds(List<Long> orderIds);

}

