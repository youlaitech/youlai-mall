package com.youlai.mall.sms.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * sms_coupon
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_coupon")
public class SmsCoupon {

    /**
     * 用户优惠券ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联优惠券模板ID
     */
    private Long templateId;

    /**
     * 领取用户
     */
    private Long userId;

    /**
     * 用户昵称（冗余字段）
     */
    private String userName;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 优惠券状态
     */
    private CouponStateEnum state;

    /**
     * 优惠券生效起始时间
     */
    private Long availableStartTime;

    /**
     * 优惠券生效起始时间
     */
    private Long availableEndTime;

    /**
     * 关联订单 ID
     */
    private Long orderId;

    /**
     * 使用时间
     */
    private Long useTime;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT)
    private String gmtCreatedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String gmtModifiedBy;

    @TableField(exist = false)
    private SmsCouponTemplate template;

}