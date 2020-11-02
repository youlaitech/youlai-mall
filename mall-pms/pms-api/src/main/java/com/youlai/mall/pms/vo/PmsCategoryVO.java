package com.youlai.mall.pms.vo;

import lombok.Data;

import java.util.List;

@Data
public class PmsCategoryVO {

    private Long id;

    private String name;

    private Long parentId;

    private Integer level;

    private String icon;

    private Integer sort;

    private String unit;

    private Integer status;

    private List<PmsCategoryVO> children;

}
