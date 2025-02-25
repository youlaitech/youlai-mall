package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品分类实体类
 *
 * @author Ray.Hao
 * @since 2024/04/20
 */
@TableName("pms_category")
@Getter
@Setter
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类图标URL
     */
    private String iconUrl;

    /**
     * 分类排序
     */
    private Integer sort;

    /**
     * 是否显示（0：不显示，1：显示）
     */
    private Integer isVisible;

    /**
     * 层级路径
     */
    private String treePath;


    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}
