package com.youlai.mall.product.model.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.youlai.mall.product.enums.AttributeInputMethodEnum;
import com.youlai.mall.product.enums.AttributeTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
public class AttributeBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性主键
     */
    private Long id;

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
     */
    private AttributeTypeEnum type;

    /**
     * 输入方式（1：手动输入，2：从列表选择）
     */
    private AttributeInputMethodEnum inputMethod;

    /**
     * 可选值列表（以逗号分隔，仅当输入方式为2时使用
     */
    private String selectableValues;

    /**
     * 属性组名称
     */
    private String attributeGroupName;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;




}
