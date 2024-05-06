package com.youlai.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Schema(description = "品牌分类视图对象")
@Getter
@Setter
public class BrandCategoryVO {


    @Schema(description = "品牌ID", example = "1")
    private Long brandId;

    @Schema(description = "品牌名称", example = "小米")
    private String brandName;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "分类名称", example = "电子产品")
    private String categoryName;

}
