package com.youlai.mall.product.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 商品SPU表单对象
 *
 * @author Ray.Hao
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

    @Schema(description = "属性值列表", example = "[{\"id\":\"1\",\"value\":\"2K\"},{\"id\":\"2\",\"value\":\"6英寸\"}]")
    private List<AttrValue> attrValues;

    @Schema(description = "SKU列表", example = "[{\"name\":\"白色 64GB\",\"price\":699900,\"stock\":100,\"imgUrl\":\"https://youlai.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg\",\"specValues\":[{\"id\":\"1\",\"value\":\"白色\"},{\"id\":\"2\",\"value\":\"64GB\"}]}]")
    private List<Sku> skuList;

    @Schema(description = "商品图片")
    @Data
    public static class Image {

        @Schema(description = "图片ID")
        private Long id;

        @Schema(description = "图片地址")
        private String imgUrl;

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

        @Schema(description = "规格值列表", example = "[{\"id\":\"1\",\"value\":\"白色\"},{\"id\":\"2\",\"value\":\"64GB\"}]")
        private List<SpecValue> specValues;

    }

    @Schema(description = "规格值")
    @Data
    public static class SpecValue {

        @Schema(description = "规格ID", example = "1")
        private Long specId;

        @Schema(description = "规格值", example = "白色")
        private String specValue;

        @Schema(description = "是否主规格(0-否 1-是)", example = "true")
        private Integer isMain;

        @Schema(description = "规格图片URL", example = "https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg")
        private String imgUrl;
    }


    @Schema(description = "属性值")
    @Data
    public static class AttrValue {

        @Schema(description = "属性ID", example = "1")
        private Long attrId;

        @Schema(description = "属性值", example = "2K")
        private String attrValue;

    }


}
