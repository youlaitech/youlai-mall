package com.youlai.mall.product.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 品牌分类关联 表单对象
 *
 * @author Ray.Hao
 * @since 2024-05-06
 */
@Getter
@Setter
@Schema(description = "品牌分类关联表单对象")
public class BrandCategoryForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "品牌ID")
    @NotNull(message = "品牌ID不能为空")
    private Long brandId;


    @Schema(description = "分类ID")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;


}
