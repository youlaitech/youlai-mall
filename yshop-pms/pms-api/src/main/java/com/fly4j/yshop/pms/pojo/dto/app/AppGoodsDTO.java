package com.fly4j.yshop.pms.pojo.dto.app;

import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
