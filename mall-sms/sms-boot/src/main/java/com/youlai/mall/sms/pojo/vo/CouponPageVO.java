package com.youlai.mall.sms.pojo.vo;

import com.youlai.mall.sms.common.enums.CouponTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@ApiModel("优惠券分页视图对象")
@Data
public class CouponPageVO {

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券类型")
    private Integer type;

    @ApiModelProperty("优惠券面值")
    private BigDecimal faceValue;

    @ApiModelProperty("使用平台")
    private Integer platform;

}
