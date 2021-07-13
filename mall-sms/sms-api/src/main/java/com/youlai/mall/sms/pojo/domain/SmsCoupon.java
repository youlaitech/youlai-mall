package com.youlai.mall.sms.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * 优惠券码
     */
    private String couponCode;

    /**
     * 优惠券状态
     */
    private CouponStateEnum state;

    /**
     * 使用时间
     */
    private Date useTime;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    @TableField(exist = false)
    private SmsCouponTemplate template;

}