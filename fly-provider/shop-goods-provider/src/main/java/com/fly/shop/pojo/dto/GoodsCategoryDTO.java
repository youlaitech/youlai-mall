package com.fly.shop.pojo.dto;


import com.fly.shop.pojo.entity.GoodsCategory;
import lombok.Data;

import java.util.List;

@Data
public class GoodsCategoryDTO   {

    private Integer categoryId;

    private String categoryName;

    private Integer parentId;

    private String treePath;

    private Integer num;

    private String unit;

    private Integer isNavbar;

    private Integer isShow;

    private Integer sort;

    private List<GoodsCategoryDTO> children;

}
