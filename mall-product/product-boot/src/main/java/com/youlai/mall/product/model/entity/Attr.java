package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
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
    private Long attrGroupId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 输入方式：1->手动输入；2->列表选择
     */
    private Integer inputType;

    /**
     * 可选值列表（以逗号分隔，仅当输入方式为2时使用）
     */
    private String options;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}
