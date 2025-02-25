package com.youlai.mall.product.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品分类表单对象
 *
 * @author Ray.Hao
 * @since 2024/04/20
 */
@Schema(description = "商品分类表单对象")
@Data
public class CategoryForm {

    @Schema(description = "分类ID", example = "1")
    private Long id;

    @Schema(description = "分类名称", example = "电子产品")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(description = "父分类ID", example = "0")
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    @Schema(description = "分类图标URL", example = "http://example.com/icon.jpg")
    private String iconUrl;

    @Schema(description = "分类排序", example = "100")
    private Integer sort;

    @Schema(description = "分类是否可见", allowableValues = {"0", "1"}, example = "1")
    private Integer isVisible;

    @Schema(description = "分类级别", example = "3")
    private Integer level;
}
