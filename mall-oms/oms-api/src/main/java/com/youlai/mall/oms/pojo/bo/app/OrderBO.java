package com.youlai.mall.oms.pojo.bo.app;

import com.youlai.common.base.BaseVO;
import com.youlai.mall.oms.pojo.domain.OmsOrderDelivery;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.pojo.domain.OmsOrderLog;
import lombok.Data;

import java.util.List;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/19
 */
@Data
public class OrderBO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

    private OmsOrderLog orderLog;

    private OmsOrderDelivery orderDelivery;

}
