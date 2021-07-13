package com.youlai.mall.sms.pojo.vo;

import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sms_coupon
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCouponVO implements Serializable {
    /**
     * 用户优惠券ID
     */
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
     * 创建时间
     */
    private Date gmtCreate;

}