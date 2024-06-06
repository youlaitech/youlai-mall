package com.youlai.mall.product.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 品牌分类关联 DTO
 *
 * @author Ray Hao
 * @since 2024-05-06
 */
@Getter
@Setter
@Schema(description = "品牌分类关联传输层对象")
public class BrandCategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "主键")

    private Long id;

    @Schema(description = "分类ID")

    private Long categoryId;

    @Schema(description = "品牌ID")

    private Long brandId;

    @Schema(description = "创建时间")

    private LocalDateTime createTime;

    @Schema(description = "创建人ID")

    private Long createBy;
}
