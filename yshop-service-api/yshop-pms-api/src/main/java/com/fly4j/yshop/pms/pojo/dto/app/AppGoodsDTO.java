package com.fly4j.yshop.pms.pojo.dto.app;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品详情
 */
@Data
@ApiModel
@Accessors
public class AppGoodsDTO {

    private Long id;

    private String name;

    private String pic_url;

    private String price;

}
