package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

/**
 * @author huawei
 * @desc 订单创建响应结果VO
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
@Data
public class OrderSubmitResultVO extends BaseVO {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderSn;
}
