package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

/**
 * @author huawei
 * @desc 优惠券信息
 * @email huawei_code@163.com
 * @date 2021/1/13
 */
@Data
public class CouponVO extends BaseVO {

    private String title;

    private Long price;
}
