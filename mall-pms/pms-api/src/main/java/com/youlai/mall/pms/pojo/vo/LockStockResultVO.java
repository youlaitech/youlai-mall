package com.youlai.mall.pms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

/**
 * @author huawei
 * @desc 订单锁定库存 VO
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@Data
public class LockStockResultVO extends BaseVO {

    private Long skuId;

    private String skuName;

    private boolean locked;
}
