package com.youlai.mall.product.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 品牌分类关联查询对象
 *
 * @author Ray Hao
 * @since 2024-05-06
 */
@Schema(description ="品牌分类关联查询对象")
@Data
public class BrandCategoryQuery {

    @Schema(description="关键字")
    private String keywords;

    @Schema(description="品牌ID")
    private Long brandId;

}
