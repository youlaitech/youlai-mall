package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import com.youlai.mall.product.enums.AttributeInputTypeEnum;
import com.youlai.mall.product.enums.AttributeTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性实体
 *
 * @author Ray
 * @since 2024/5/24
 */
@Getter
@Setter
@TableName("pms_attr")
public class Attr extends BaseEntity {

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
    private AttributeTypeEnum type;

    /**
     * 输入方式（1：手动输入，2：从列表选择）
     */
    private AttributeInputTypeEnum inputType;

    /**
     * 可选值列表（以逗号分隔，仅当输入方式为2时使用）
     */
    private String selectableValues;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 排序
     */
    private Integer sort;
}
