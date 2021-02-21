package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huawei
 * @desc 支付订单详情模型
 * @email huawei_code@163.com
 * @date 2021/2/21
 */
@ApiModel("支付订单详情模型")
@Data
public class PayInfoVO extends BaseVO {

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private Long payPrice;

    /**
     * 会员余额
     */
    @ApiModelProperty("会员余额")
    private Long balance;
}
