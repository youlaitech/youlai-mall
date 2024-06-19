package com.youlai.mall.sms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 优惠券领取历史记录
 */
@TableName(value ="sms_coupon_history")
@Data
public class CouponHistory implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员昵称
     */
    private String memberNickname;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 获取类型(1：后台增删；2：主动领取)
     */
    private Integer  getType;

    /**
     * 状态(0：未使用；1：已使用；2：已过期)
     */
    private Integer  status;

    /**
     * 使用时间
     */
    private Date useTime;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}