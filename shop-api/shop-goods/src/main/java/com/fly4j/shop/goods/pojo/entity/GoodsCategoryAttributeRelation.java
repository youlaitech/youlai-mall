package com.fly4j.shop.goods.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("goods_category_attribute_relation")
@Accessors(chain = true)
public class GoodsCategoryAttributeRelation {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer categoryId;
    private Integer attributeId;
}
