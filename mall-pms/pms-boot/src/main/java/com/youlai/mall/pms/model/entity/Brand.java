package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Data;


/**
 * 品牌实体
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@TableName("pms_brand")
@Data
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
     * 用于展示在列表中的排序权重，数值越小，排序越靠前。
     */
    private Integer sort;
}
