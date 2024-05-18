package com.youlai.mall.product.model.form;

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

    @Schema(description = "商品名称", example = "Apple iPhone 12")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "品牌ID", example = "1")
    private Long brandId;

    @Schema(description = "商品主图URL", example = "https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg")
    private String imgUrl;

    @Schema(description = "商品图册(详情页轮播图)", example = "[{\"imgUrl\":\"https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg\",\"sort\":1}]")
    private List<Image> imgList;

    @Schema(description = "商品单位", example = "台")
    private String unit;

    @Schema(description = "商品描述", example = "Apple iPhone 12 64GB 绿色")
    private String description;

    @Schema(description = "商品详情", example = "<p>Apple iPhone 12 64GB 绿色</p>")
    private String detail;

    @Schema(description = "商品属性列表(仅返回用，提交时不填)", example = "[{\"attributeId\":1,\"attributeName\":\"颜色\",\"attributeValues\":[\"红色\",\"绿色\"]}]")
    private List<SaleAttribute> saleAttributes;

    @Schema(description = "基础属性分组列表(仅返回用，提交时不填)", example = "[{\"attributeGroupId\":1,\"attributeGroupName\":\"基础属性\",\"attributeAttributeValues\":[{\"id\":1,\"attributeId\":1,\"attributeName\":\"颜色\",\"attributeValue\":\"红色\"}]}]")
    private List<AttributeGroup> attributeGroups;

    @Schema(description = "SKU列表", example = "[{\"name\":\"绿色 64GB\",\"price\":699900,\"stock\":100,\"imgUrl\":\"https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg\",\"saleAttributeValues\":[{\"attributeName\":\"颜色\",\"attributeValue\":\"绿色\",\"attributeId\":1},{\"attributeName\":\"内存\",\"attributeValue\":\"64GB\",\"attributeId\":2}]}]")
    private List<Sku> skuList;

    @Schema(description = "商品图片")
    @Data
    public static class Image {

        @Schema(description = "图片ID")
        private Long id;

        @Schema(description = "图片地址")
        private String imgUrl;

        @Schema(description = "排序")
        private Integer sort;

    }

    @Schema(description = "属性分组")
    @Data
    public static class AttributeGroup {

        @Schema(description = "属性分组ID")
        private Long attributeGroupId;

        @Schema(description = "属性分组名称")
        private String attributeGroupName;

        @Schema(description = "属性列表")
        private List<AttributeValue> attributeValues;

    }

    @Schema(description = "属性值对象")
    @Data
    public static class AttributeValue {

        @Schema(description = "属性值ID")
        private Long id;

        @Schema(description = "属性ID")
        private Long attributeId;

        @Schema(description = "属性名称")
        private String attributeName;

        @Schema(description = "属性值")
        private String attributeValue;

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

        @Schema(description = "商品图片URL")
        private String imgUrl;

        @Schema(description = "商品销售属性值")
        private List<AttributeValue> saleAttributeValues;

    }

    @Schema(description = "销售属性")
    @Data
    public static class SaleAttribute {

        @Schema(description = "属性ID", example = "1")
        private Long attributeId;

        @Schema(description = "属性名称", example = "颜色")
        private String attributeName;

        @Schema(description = "属性值列表", example = "[\"白色\",\"黑色\"]")
        private List<String> attributeValues;
    }
}
