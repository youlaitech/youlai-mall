package com.youlai.mall.sms.pojo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sms_coupon_record
 * @author 
 */
@Data
public class SmsCouponRecord implements Serializable {
    private Long id;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 使用状态  可用 NEW,已使用USED,过期 EXPIRED;
     */
    private String useState;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称（冗余字段）
     */
    private String userName;

    /**
     * 1满减券 2叠加满减券 3无门槛券
     */
    private Integer couponType;

    /**
     * 优惠券标题
     */
    private String couponTitle;

    /**
     * 满多少才可以使用（为0则不限制金额）
     */
    private Long conditionPrice;

    /**
     * 抵扣价格
     */
    private Long price;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 逻辑删除使用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}