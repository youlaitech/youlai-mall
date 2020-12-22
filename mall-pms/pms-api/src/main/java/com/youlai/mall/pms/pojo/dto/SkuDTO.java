package com.youlai.mall.pms.pojo.dto;


import lombok.Data;

import java.util.List;

@Data
public class SkuDTO {

    private Long id;
    private Long spuId;
    private String name;
    private String code;
    private String picUrl;
    private Long originPrice;
    private Long price;
    private Integer stock;
    private List<Long> specValueIds;
}
