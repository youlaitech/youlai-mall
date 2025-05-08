package com.youlai.mall.sale.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券-商品关联表
 */

@TableName(value ="sms_coupon_spu")
@Data
@Accessors(chain = true)
public class CouponSpu implements Serializable {
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
     * 商品ID
     */
    private Long spuId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商品名称（非数据库字段）
     */
    @TableField(exist = false)
    private String spuName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}