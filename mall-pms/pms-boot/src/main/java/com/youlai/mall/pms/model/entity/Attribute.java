package com.youlai.mall.pms.model.entity;

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
     * 输入录入方式：1-手动输入，2-从列表选择
     */
    private Integer inputType;

    /**
     * 逗号分割的可选值列表，仅当input_type是2使用
     */
    private String options;


    /**
     * 逻辑删除标识(0-未删除，1-已删除)
     */
    private Integer isDeleted;
    ;
}
