package com.youlai.mall.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 锁定库存传输对象
 *
 * @author Ray
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockSkuDTO {

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 商品数量
     */
    private Integer quantity;


}
