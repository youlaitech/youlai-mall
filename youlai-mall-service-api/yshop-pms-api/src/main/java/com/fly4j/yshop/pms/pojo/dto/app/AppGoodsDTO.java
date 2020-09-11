package com.fly4j.yshop.pms.pojo.dto.app;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 商品详情
 */
@Data
@ApiModel
@Accessors
public class AppGoodsDTO {

    private Integer id;

    private String name;

    private String pic_url;

    private BigDecimal retail_price;

    private BigDecimal counter_price;
}
