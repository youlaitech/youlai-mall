package com.youlai.mall.sale.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券表
 *
 * @author Ray.Hao
 * @since 2024/6/7
 */
@EqualsAndHashCode(callSuper = false)
@TableName("sms_coupon")
@Data
public class Coupon extends BaseEntity {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券兑换码
     */
    private String code;

    /**
     * 优惠券类型(1-满减券;2-直减券;3-折扣券;4-包邮券)
     */
    private Integer type;

    /**
     * 优惠金额/折扣值
     */
    private BigDecimal discountAmount;

    /**
     * 最高折扣金额
     */
    private BigDecimal discountLimit;

    /**
     * 使用门槛金额
     */
    private BigDecimal minAmount;

    /**
     * 优惠券状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 发行数量(-1表示不限制)
     */
    private Integer totalCount;

    /**
     * 已领取数量
     */
    private Integer receiveCount;

    /**
     * 已使用数量
     */
    private Integer usedCount;

    /**
     * 每人限领数量(-1表示不限制)
     */
    private Integer perLimit;

    /**
     * 有效期类型(1-固定日期范围;2-领取后天数)
     */
    private Integer validType;

    /**
     * 领取后有效天数
     */
    private Integer validDays;

    /**
     * 有效期开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validStartTime;

    /**
     * 有效期结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validEndTime;

    /**
     * 使用范围(位运算: 1-全场通用(0001);2-指定分类(0010);4-指定商品(0100))
     */
    private Integer useScope;

    /**
     * 是否可叠加使用(0-否;1-是)
     */
    private Integer canSuperimpose;

    /**
     * 是否仅首单可用(0-否;1-是)
     */
    private Integer firstOrderOnly;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 是否删除(0-否;1-是)
     */
    @TableLogic
    private Integer isDeleted;
}