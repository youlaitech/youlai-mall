package com.fly.shop.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * Created by XianRui on 2020-03-09 21:23
 **/

@Data
@TableName("goods_attribute")
public class GoodsAttribute  extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer attributeId;

    private String attributeName;

    private Integer attributeTypeId;

    /**
     * 属性的类型；0->属性；1->参数
     */
    private Integer type;

    /**
     * 属性选择类型：0->唯一；1->单选；2->多选
     */
    private Integer selectType;

    /**
     * 属性录入方式：0->手工录入；1->从列表中选取
     */
    private Integer inputType;

    /**
     * 可选值列表，以逗号隔开
     */
    private String inputList;

    private Integer sort;

    /**
     * 分类筛选样式：0->普通；1->颜色
     */
    private Integer filterType;

    /**
     * 检索类型： 0->不需要进行检索；1->关键字检索；2->范围检索
     */
    private Integer searchType;

    /**
     * 相同属性产品是否关联；0->不关联；1->关联
     */
    private Integer isRelated;

    /**
     * 是否支持手动新增；0->不支持；1->支持
     */
    private Integer isHandAdd;

    @TableField(exist = false)
    private String attributeTypeName;
}
