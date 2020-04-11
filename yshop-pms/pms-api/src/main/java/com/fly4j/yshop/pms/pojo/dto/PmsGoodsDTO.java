package com.fly4j.yshop.pms.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


public class PmsGoodsDTO {

    private long id;
    private String name;
    private String goodsSn;
    private long categoryId;
    private long brandId;
    private String keywords;
    private String subtitle;
    private String description;
    private String picUrl;
    private String album;
    private String unit;
    private double marketPrice;
    private double retailPrice;
    private long sort;
    private String detail;
    private long isNew;
    private long isHot;
    private long isSale;
    private long isDelete;


}
