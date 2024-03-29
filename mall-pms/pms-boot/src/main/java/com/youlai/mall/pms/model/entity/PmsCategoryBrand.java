package com.youlai.mall.pms.model.entity;

import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * 分类品牌
 *
 * @author haoxr
 * @since 2022/7/2
 */
@Data
public class PmsCategoryBrand extends BaseEntity {
    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

}
