package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性组
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@TableName("pms_attribute_group")
public class AttributeGroup extends BaseEntity {

    /**
     * 属性组名称
     */
    private String name;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 备注
     */
    private String remark;


    /**
     * 逻辑删除标识(0-未删除，1-已删除)
     */
    private Integer isDeleted;;
}
