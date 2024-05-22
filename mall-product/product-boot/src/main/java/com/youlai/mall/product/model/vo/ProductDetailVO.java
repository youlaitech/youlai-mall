package com.youlai.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品详情视图对象
 * <p>
 * NutUI 商品数据结构：<a href="https://nutui.jd.com/h5/vue/4x/#/zh-CN/component/sku">Nut UI SKU 规格</a>
 *
 * @author Ray Hao
 * @since 2024/5/20
 */
@Schema(description = "商品详情")
@Data
public class ProductDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(description = "商品信息")
    private Goods goods;

    @Schema(description = "销售属性列表")
    private List<Attribute> attributes;

    @Schema(description = "SKU 列表")
    private List<Sku> skus;

    @Schema(description = "商品信息")
    @Data
    public static class Goods {

        @Schema(description = "SKU ID")
        private Long skuId;

        @Schema(description = "价格")
        private Long price;

        @Schema(description = "图片路径")
        private String imagePath;
    }

    @Schema(description = "SKU 属性列表")
    @Data
    public static class Attribute {

        @Schema(description = "属性ID")
        private Long id;

        @Schema(description = "属性名称")
        private String name;

        @Schema(description = "属性值列表")
        private List<AttributeValue> list;
    }

    @Schema(description = "属性值")
    @Data
    public static class AttributeValue {

        @Schema(description = "属性值 ID")
        private String id;

        @Schema(description = "属性值名称")
        private String name;

        @Schema(description = "是否激活")
        private Boolean active;

        @Schema(description = "是否禁用")
        private Boolean disable;
    }


    @Schema(description = "SKU 信息")
    @Data
    public static class Sku{

        @Schema(description = "SKU ID")
        private Long id;

        @Schema(description = "SKU 名称")
        private String name;

        @Schema(description = "SKU 编码")
        private String code;

        @Schema(description = "SKU 价格（单位：分）")
        private Long price;

        @Schema(description = "SKU 库存")
        private Integer stock;

        @Schema(description = "SKU 图片")
        private String imgUrl;

        @Schema(description = "SKU 属性值列表",example = "[\"白色\",\"8+256G\"]")
        private List<String> attributeValues;

    }

}
