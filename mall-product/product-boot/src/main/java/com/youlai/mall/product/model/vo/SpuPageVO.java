package com.youlai.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 【管理端】商品分页视图对象
 *
 * @author Ray.Hao
 * @since 2022/3/13
 */
@Schema(description = "商品分页视图对象")
@Data
@Accessors(chain = true)
public class SpuPageVO {

    @Schema(description = "SPU ID")
    private Long id;

    @Schema(description = "SPU名称")
    private String name;

    @Schema(description = "商品主图URL")
    private String imgUrl;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品状态(1-已上架，0-未上架)")
    private Integer status;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "品牌名称")
    private String brandName;
}
