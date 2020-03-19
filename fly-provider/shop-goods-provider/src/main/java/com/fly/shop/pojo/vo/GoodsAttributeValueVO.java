package com.fly.shop.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GoodsAttributeValueVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long goodsId;

    private Long goodsAttributeId;

    /**
     * 手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     */
    private String value;

}