package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_item")
public class OrderItem extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String orderSn;

    private Long goodsId;

    private String goodsPic;

    private String goodsName;

    private String goodsBrand;

    private String goodsSn;

    private BigDecimal goodsPrice;

    private Integer goodsQuantity;

    private Long goodsSkuId;

    private String goodsSkuCode;

    private Long goodsCategoryId;

    private String promotionName;

    private BigDecimal promotionAmount;

    private BigDecimal couponAmount;

    private BigDecimal integrationAmount;

    private BigDecimal realAmount;

    private Integer giftIntegration;

    private Integer giftGrowth;

    private String goodsAttr;

}