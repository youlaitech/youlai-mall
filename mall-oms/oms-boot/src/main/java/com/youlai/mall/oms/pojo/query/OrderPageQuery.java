package com.youlai.mall.oms.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/1 19:14
 */
@Data
@ApiModel("订单分页查询对象")
public class OrderPageQuery extends BasePageQuery {

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("会员ID")
    private Long memberId;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty(value = "开始时间(格式：yyyy-MM-dd)")
    private String beginDate;

    @ApiModelProperty(value = "截止时间(格式：yyyy-MM-dd)")
    private String endDate;

}
