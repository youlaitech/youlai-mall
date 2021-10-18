package com.youlai.mall.sms.pojo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sms_coupon
 * @author 
 */
@Data
public class SmsCoupon implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 优惠券标题（有图片则显示图片）：无门槛50元优惠券 | 单品最高减2000元
     */
    private String title;

    /**
     * 图片
     */
    private String img;

    /**
     * 1满减券 2叠加满减券 3无门槛券（需要限制大小）
     */
    private Integer type;

    /**
     * 发布状态, PUBLISH发布，DRAFT草稿，OFFLINE下线
     */
    private String publish;

    /**
     * 满多少才可以使用（为0则不限制金额）
     */
    private Long conditionPrice;

    /**
     * 抵扣价格
     */
    private Long price;

    /**
     * 优惠券总量
     */
    private Integer publishCount;

    /**
     * 每张优惠券限领张数（默认为1，为0不限制）
     */
    private Integer limitCount;

    /**
     * 已领取的优惠券数量
     */
    private Integer takeCount;

    /**
     * 已使用的优惠券数量
     */
    private Integer usedCount;

    /**
     * 发放开始时间
     */
    private Date startTime;

    /**
     * 发放结束时间
     */
    private Date endTime;

    /**
     * 自领取之日起有效天数
     */
    private Integer validDays;

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