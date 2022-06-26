package com.youlai.mall.sms.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券表单对象
 *
 * @author haoxr
 * @date 2022/6/23
 */
@ApiModel("优惠券表单对象")
@Data
public class CouponForm {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券类型(1:满减券;2:直减券;3:折扣券)")
    private Integer type;

    @ApiModelProperty("优惠券面值金额(单位:分)")
    private Long faceValue;

    @ApiModelProperty("优惠券折扣")
    private BigDecimal discount;

    @ApiModelProperty("优惠券码")
    private String code;

    @ApiModelProperty("优惠券状态(0:未发布;1:已发布;2:已结束;)")
    private Integer status;

    @ApiModelProperty("使用平台(0:全部;1:移动端;2:PC;)")
    private Integer platform;

    @ApiModelProperty("优惠券总数(0:无限制)")
    private Integer totalCount;

    @ApiModelProperty("最低消费金额(0:无门槛)")
    private Long minPoint;

    @ApiModelProperty("每人限领张数(0:不限制)")
    private Integer perLimit;

    @ApiModelProperty("有效期类型(1:自领取之日起有效天数;2:有效起止时间)")
    private Integer validType;

    @ApiModelProperty("自领取之日起有效天数")
    private Integer validDays;

    @ApiModelProperty("有效期开始时间")
    private Date validBeginTime;

    @ApiModelProperty("有效期截止时间")
    private Date validEndTime;

    @ApiModelProperty("使用类型(0:全场通用;1:指定分类;2:指定商品)")
    private Integer useType;

    @ApiModelProperty("备注")
    private String remark;

}
