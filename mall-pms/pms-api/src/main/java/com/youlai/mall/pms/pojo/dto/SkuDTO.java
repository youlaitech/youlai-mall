package com.youlai.mall.pms.pojo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class SkuDTO implements Serializable {

    private Long id;
    private Long spuId;
    private String name;
    private String code;
    private String picUrl;
    private Long originPrice;
    private Long price;
    private Integer stock;
    private String specValueIds;
}
