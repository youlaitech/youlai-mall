package com.youlai.mall.oms.pojo.bo.app;

import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
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

}
