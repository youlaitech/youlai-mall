package com.fly4j.shop.goods.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly4j.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("goods_category")
public class GoodsCategory extends BaseEntity {

    @TableId(type = IdType.AUTO)
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

}
