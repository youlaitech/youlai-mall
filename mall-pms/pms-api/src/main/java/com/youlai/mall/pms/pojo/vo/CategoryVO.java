package com.youlai.mall.pms.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryVO {

    private Long id;

    private String name;

    private Long parentId;

    private Integer level;

    private String icon;

    private Integer sort;

    private String unit;

    private Integer status;

    private List<CategoryVO> children=new ArrayList<>();

}
