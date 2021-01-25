package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import com.youlai.mall.oms.pojo.entity.OrderDeliveryEntity;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.entity.OrderGoodsEntity;
import com.youlai.mall.oms.pojo.entity.OrderLogsEntity;
import lombok.Data;

import java.util.List;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/19
 */
@Data
public class OrderVO extends BaseVO {

    private OrderEntity orderEntity;

    private List<OrderGoodsEntity> orderGoods;

    private OrderLogsEntity orderLogsEntity;

    private OrderDeliveryEntity orderDeliveryEntity;

}
