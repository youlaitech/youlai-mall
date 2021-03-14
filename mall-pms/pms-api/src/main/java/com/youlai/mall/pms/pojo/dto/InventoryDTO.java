package com.youlai.mall.pms.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @description 库存数量
 * @author haoxr
 * @createTime 2021-03-07 15:14
 */
@Data
@Builder
public class InventoryDTO {

    @ApiModelProperty("库存ID")
    private Long skuId;

    @ApiModelProperty("数量")
    private Integer num;

}
