package com.youlai.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "商品分类视图对象")
@Getter
@Setter
public class CategoryVO {

    @Schema(description = "分类ID", example = "1")
    private Long id;

    @Schema(description = "分类名称", example = "电子产品")
    private String name;

    @Schema(description = "父分类ID", example = "0")
    private Long parentId;

    @Schema(description = "分类图标URL", example = "http://example.com/icon.jpg")
    private String iconUrl;

    @Schema(description = "分类排序", example = "100")
    private Integer sort;

    @Schema(description = "分类是否可见", allowableValues = {"0", "1"}, example = "1")
    private Integer isVisible;

    @Schema(description = "分类级别", example = "1")
    private Integer level;

    @Schema(description = "子分类列表")
    private List<CategoryVO> children;
}
