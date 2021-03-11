package com.youlai.mall.pms.pojo.dto;

import lombok.Data;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/13
 */
@Data
public class SkuDTO {

    private Long id;

    private String code;

    private String name;

    private String pic;

    private Long originPrice;

    private Long price;

    private Integer inventory;

    private Long spuId;

    private String productName;

    private String productPic;

    private Long brandId;

    private String brandName;

    private Long categoryId;

    private String categoryName;

}
