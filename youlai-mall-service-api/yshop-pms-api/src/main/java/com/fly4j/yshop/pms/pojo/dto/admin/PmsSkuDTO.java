package com.fly4j.yshop.pms.pojo.dto.admin;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class PmsSkuDTO {
    private Integer id;
    private String spu_name;
    private String spu_code;
    private String specs;
    private BigDecimal price;
    private Integer stock;
    private String pic_url;
}
