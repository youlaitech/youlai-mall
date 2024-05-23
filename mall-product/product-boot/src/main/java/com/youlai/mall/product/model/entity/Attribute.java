package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性实体
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@TableName("pms_attribute")
public class Attribute extends BaseEntity {

    /**
     * 属性组主键
     */
    private Long attributeGroupId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性类型（1：基础属性，2：销售属性）
     * @see com.youlai.mall.product.enums.AttributeTypeEnum
     */
    private Integer type;

    /**
     * 输入方式（1：手动输入，2：从列表选择）
     */
    private Integer inputMethod;

    /**
     * 可选值列表（以逗号分隔，仅当输入方式为2时使用
     */
    private String selectableValues;

    /**
     * 分类ID
     */
    private Long categoryId;
}
