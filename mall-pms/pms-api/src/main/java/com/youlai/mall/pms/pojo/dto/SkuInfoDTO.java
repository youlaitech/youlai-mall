package com.youlai.mall.pms.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/5 23:09
 */

@Data
public class SkuInfoDTO {
    /**
     * skuId
     */
    private Long skuId;
    /**
     * SKU 编号
     */
    private String skuSn;
    /**
     * SKU 名称
     */
    private String skuName;
    /**
     * SKU 图片地址
     */
    private String picUrl;
    /**
     * SKU 价格
     */
    private Long price;
    /**
     * SKU 库存数量
     */
    private Integer stockNum;
    /**
     * SPU 名称
     */
    private String spuName;
}
