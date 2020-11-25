package com.youlai.mall.oms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;


@Data
public class OmsOrderSku extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private Long orderId;

    private String orderSn;

    private Long spuId;

    private String spuName;

    private String skuBarCode;

    private String skuSpecifications;

    private Long skuPrice;

    private Integer skuQuantity;

    private String pic;

}
