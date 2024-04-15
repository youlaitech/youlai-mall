package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 分类品牌
 *
 * @author haoxr
 * @since 2022/7/2
 */
@TableName("pms_category_brand")
@Data
public class CategoryBrand {
    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

}
