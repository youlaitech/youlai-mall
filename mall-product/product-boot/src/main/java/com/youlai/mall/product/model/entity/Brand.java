package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 品牌实体
 *
 * @author Ray.Hao
 * @since 2024-04-19
 */
@TableName("pms_brand")
@Getter
@Setter
public class Brand extends BaseEntity {

    /**
     * 品牌的名称。
     */
    private String name;

    /**
     * 品牌的LOGO图片的URL地址。
     */
    private String logoUrl;

    /**
     * 品牌的首字母。
     */
    private String firstLetter;

    /**
     * 用于展示在列表中的排序权重，数值越小，排序越靠前。
     */
    private Integer sort;

    /**
     * 是否显示[0-不显示，1-显示]
     */
    private Integer isVisible;

    /**
     * 品牌的简介
     */
    private String description;


    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;
}
