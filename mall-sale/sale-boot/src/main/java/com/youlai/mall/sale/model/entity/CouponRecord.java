package com.youlai.mall.sale.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券领取记录表
 */
@TableName(value ="sms_coupon_record")
@Data
@Accessors(chain = true)
public class CouponRecord implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 获取类型(1-主动领取;2-后台发放)
     */
    private Integer getType;

    /**
     * 使用状态(0-未使用;1-已使用;2-已过期)
     */
    private Integer status;

    /**
     * 使用时间
     */
    private Date useTime;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 使用优惠金额
     */
    private BigDecimal useAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
} 