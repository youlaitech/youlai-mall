package com.youlai.mall.order.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单商品明细
 *
 * @author huawei
 * @since 2020-12-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OmsOrderItem extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * SKU 编号
     */
    private String skuCode;

    /**
     * 规格名称
     */
    private String skuName;

    /**
     * 商品sku图片
     */
    private String imgUrl;

    /**
     * 商品单价(单位：分)
     */
    private Long price;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品总金额(单位：分)
     */
    private Long totalFee;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;


}
