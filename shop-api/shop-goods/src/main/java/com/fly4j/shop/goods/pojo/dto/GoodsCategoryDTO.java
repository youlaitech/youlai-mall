package com.fly4j.shop.goods.pojo.dto;


import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class GoodsCategoryDTO   {

    private Integer categoryId;

    private String categoryName;

    private Integer parentId;

    private Integer goodsCount;

    private String goodsUnit;

    private Integer sort;

    private String icon;

    private Integer isNav;

    private Integer isShow;

    private String keywords;

    private String description;

    private List<GoodsCategoryDTO> children;

    private Set<Integer> goodsAttributeIds;

}
