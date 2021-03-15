package com.youlai.mall.sms.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author huawei
 * @desc 优惠券提交表单
 * @email huawei_code@163.com
 * @date 2021/3/14
 */
@ApiModel("优惠券提交表单")
@Data
public class CouponForm {

    /**
     * ID
     */
    private Long id;

    /**
     * 优惠券标题（有图片则显示图片）：无门槛50元优惠券 | 单品最高减2000元
     */
    @ApiModelProperty("优惠券标题")
    @NotBlank(message = "请填写优惠券标题")
    private String title;

    /**
     * 图片
     */
    @ApiModelProperty("优惠券图片")
    private String img;

    /**
     * 1满减券 2叠加满减券 3无门槛券（需要限制大小）
     */
    @ApiModelProperty("优惠券类型")
    private Integer type;

    /**
     * 满多少才可以使用（为0则不限制金额）
     */
    @ApiModelProperty("优惠券满减金额（为0则不限制金额）")
    private Long conditionPrice;

    /**
     * 抵扣价格
     */
    @ApiModelProperty("优惠券抵扣价格")
    @NotNull(message = "请填写优惠券抵扣金额")
    @Min(value = 1,message = "抵扣价格不能小于1")
    private Long price;

    /**
     * 优惠券总量
     */
    @ApiModelProperty("优惠券总量")
    @NotNull(message = "请填写优惠券总量")
    @Min(value = 1,message = "优惠券总量不能小于1")
    private Integer publishCount;

    /**
     * 每张优惠券限领张数（默认为1，为0不限制）
     */
    @ApiModelProperty("优惠券限领张数")
    @Min(value = 0,message = "优惠券限领张数不能为负数")
    private Integer limitCount;

    /**
     * 发放开始时间
     */
    @ApiModelProperty("优惠券发放开始时间")
    @NotNull(message = "请填写优惠券发放开始时间")
    private Date startTime;

    /**
     * 发放结束时间
     */
    @ApiModelProperty("优惠券发放结束时间")
    @NotNull(message = "请填写优惠券发放结束时间")
    private Date endTime;

    /**
     * 自领取之日起有效天数
     */
    @ApiModelProperty("自领取之日起有效天数")
    @NotNull(message = "请填写正确的有效天数")
    @Min(value = 1,message = "有效天数总量不能小于1")
    private Integer validDays;

}
