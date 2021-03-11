package com.youlai.mall.pms.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class SpuDTO {

    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private Long originPrice;
    private Long price;
    private Integer sales;
    private String pic;
    private List<String> pics;
    private String unit;
    private String description;
    private String detail;
    private Integer status;
}
