package com.youlai.mall.pms.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 商品SPU表单对象
 *
 * @author Ray Hao
 * @since 2024/4/23
 */
@Schema(description = "商品SPU表单对象")
@Data
public class SpuForm {

    @Schema(description = "商品ID")
    private Long id;

    @Schema(description = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "状态：1-在售，0-下架")
    private Integer status;

    @Schema(description = "商品主图URL")
    private String imgUrl;

    @Schema(description = "商品图册(详情页轮播图)")
    private List<SpuImage> galleryImages;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品详情")
    private String detail;

    @Schema(description = "商品属性列表")
    private List<SpuAttribute> attrList;

    @Schema(description = "商品规格列表")
    private List<SpuSpec> specList;

    @Schema(description = "SKU列表")
    private List<Sku> skuList;

    @Schema(description = "商品图片")
    @Data
    public static class SpuImage {

        @Schema(description = "图片ID")
        private Long id;

        @Schema(description = "图片地址")
        private String imgUrl;

        @Schema(description = "排序")
        private Integer sort;

    }

    @Schema(description = "商品属性")
    @Data
    public static class SpuAttribute {

        @Schema(description = "属性ID")
        private Long id;

        @Schema(description = "属性名称")
        private String name;

        @Schema(description = "属性值")
        private String value;

        @Schema(description = "排序")
        private Integer sort;

    }

    @Schema(description = "商品规格")
    @Data
    public static class SpuSpec {

        @Schema(description = "规格ID")
        private Long id;

        @Schema(description = "规格名称(如：颜色)")
        private String name;

        @Schema(description = "规格值列表(如：黑色、白色)")
        private List<String> values;

    }

    @Schema(description = "SKU")
    @Data
    public static class Sku {

        @Schema(description = "SKU ID")
        private Long id;

        @Schema(description = "SKU 名称")
        private String name;

        @Schema(description = "价格(单位：分)")
        private Long price;

        @Schema(description = "库存")
        private Integer stock;

    }
}
