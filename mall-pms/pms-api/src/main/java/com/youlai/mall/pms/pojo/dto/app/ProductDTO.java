package com.youlai.mall.pms.pojo.dto.app;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Long price;
    private Integer sales;
    private String pic;
}
