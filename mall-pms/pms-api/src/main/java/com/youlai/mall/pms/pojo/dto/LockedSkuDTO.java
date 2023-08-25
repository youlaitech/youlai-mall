package com.youlai.mall.pms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 锁定库存传输对象
 *
 * @author haoxr
 * @since 2.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockedSkuDTO {

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 商品数量
     */
    private Integer quantity;


    /**
     * 商品编码
     */
    private String skuSn;



}
