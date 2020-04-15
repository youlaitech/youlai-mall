package com.fly4j.yshop.pms.pojo.dto;
import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("商品传输层实体")
public class PmsSpuDTO {

    @ApiModelProperty("商品基本信息")
    private PmsSpu spu;

    @ApiModelProperty("商品属性")
    private List<PmsAttribute> attribute_list;

    @ApiModelProperty("商品规格")
    private List<PmsSpec> spec_list;

    @ApiModelProperty("商品SKU集合")
    private List<PmsSku> sku_list;

}
