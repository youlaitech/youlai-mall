package com.youlai.mall.pms.pojo.dto;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/13
 */
@Data
public class SkuInfoDTO extends BaseVO {

    private Long id;

    /**
     * 商品sku id
     */
    private Long skuId;
    /**
     * 商品sku编号
     */
    private String skuCode;
    /**
     * 商品sku名字
     */
    private String skuName;
    /**
     * 商品sku图片
     */
    private String skuPic;

    /**
     * 商品原始价格
     */
    private Long skuOriginPrice;
    /**
     * 商品sku价格(分)
     */
    private Long skuPrice;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * spu_id
     */
    private Long spuId;
    /**
     * spu_name
     */
    private String spuName;
    /**
     * spu_pic
     */
    private String spuPic;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品分类id
     */
    private Long categoryId;
    /**
     * 商品分类名称
     */
    private String categoryName;

}
