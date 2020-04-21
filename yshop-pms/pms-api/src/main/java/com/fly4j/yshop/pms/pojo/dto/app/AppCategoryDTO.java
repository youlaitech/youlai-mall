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
public class AppCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String icon_url;
}
