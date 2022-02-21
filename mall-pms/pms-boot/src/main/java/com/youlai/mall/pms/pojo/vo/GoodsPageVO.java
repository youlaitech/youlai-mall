package com.youlai.mall.pms.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品分页对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/8/8
 */
@ApiModel("商品分页对象")
@Data
public class GoodsPageVO {

    @ApiModelProperty("商品ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品价格(单位：分)")
    private Long price;

    @ApiModelProperty("销量")
    private Integer sales;

    @ApiModelProperty("图片地址")
    private String picUrl;

}
