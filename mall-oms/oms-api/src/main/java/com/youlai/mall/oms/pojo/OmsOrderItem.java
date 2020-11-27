package com.youlai.mall.oms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;


/**
 * 订单明细
 */
@Data
public class OmsOrderItem extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long spuId;

    private String spuName;

    private String skuId;

    private String skuBarCode;

    private String skuSpecifications;

    private Long skuPrice;

    private Integer skuQuantity;

    private String skuPic;

}
