package com.youlai.mall.sms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import com.youlai.mall.sms.common.enums.CouponTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券表
 * @TableName sms_coupon
 */
@TableName(value ="sms_coupon")
@Data
public class SmsCoupon extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券类型(1:满减券;2:直减券;3:折扣券)
     */
    private Integer type;

    /**
     * 优惠券码
     */
    private String code;

    /**
     * 优惠券状态(0:未发布;1:已发布;2:已结束)
     */
    private Integer status;

    /**
     * 使用平台(0:全部;1:移动端;2:PC;)
     */
    private Integer platform;

    /**
     * 优惠券面值金额(单位:分)
     */
    private Long faceValue;

    /**
     * 优惠券折扣
     */
    private BigDecimal discount;

    /**
     * 优惠券总数(0:无限制)
     */
    private Integer totalCount;

    /**
     * 使用门槛(0:无门槛)
     */
    private Long minPoint;

    /**
     * 每人限领张数(0:不限制)
     */
    private Integer perLimit;

    /**
     * 有效期类型(0:自领取之日起有效天数;1:有效起止时间)
     */
    private Integer validType;

    /**
     * 自领取之日起有效天数
     */
    private Integer validDays;

    /**
     * 有效期开始时间
     */
    private Date validBeginTime;

    /**
     * 有效期截止时间
     */
    private Date validEndTime;

    /**
     * 使用类型(0:全场通用;1:指定分类;2:指定商品)
     */
    private Integer useType;

    /**
     * 已领取的优惠券数量(统计)
     */
    private Integer receivedCount;

    /**
     * 已使用的优惠券数量(统计)
     */
    private Integer usedCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除标识(0:正常;1:删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

}