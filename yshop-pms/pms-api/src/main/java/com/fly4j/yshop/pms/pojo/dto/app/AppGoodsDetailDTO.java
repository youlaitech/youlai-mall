package com.fly4j.yshop.pms.pojo.dto.app;

import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情
 */
@Data
@ApiModel
@Accessors
public class AppGoodsDetailDTO {

    private Long id;

    private String name;

    private String subtitle;

    private String description;

    private String pic_url;

    private List<String> pic_urls;

    private String detail;

    @ApiModelProperty("专柜价")
    private BigDecimal counter_price;

    @ApiModelProperty("零售价")
    private BigDecimal retail_price;

    private Integer sales_volume;

    @ApiModelProperty("商品属性列表")
    private List<AppAttributeDTO> attribute_list;

    private List<AppSpecDTO> spec_list;

    private AppSkuDTO sku;

}
