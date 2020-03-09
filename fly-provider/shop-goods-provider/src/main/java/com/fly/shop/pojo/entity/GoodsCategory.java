package com.fly.shop.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("goods_category")
public class GoodsCategory extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer categoryId;

    private String categoryName;

    private Integer parentId;

    private String treePath;

    private Integer num;

    private String unit;

    private Integer isNavbar;

    private Integer isShow;

    private Integer sort;

}
