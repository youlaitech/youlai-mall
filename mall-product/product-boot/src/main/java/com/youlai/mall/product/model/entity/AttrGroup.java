package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性组
 *
 * @author Ray
 * @since 2024-04-19
 */
@Getter
@Setter
@TableName("pms_attr_group")
public class AttrGroup extends BaseEntity {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 属性组名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;


}
