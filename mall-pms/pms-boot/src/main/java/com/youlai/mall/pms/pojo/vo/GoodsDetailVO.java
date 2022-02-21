package com.youlai.mall.pms.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品详情视图对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/8/8
 */
@Data
@ApiModel("商品详情")
public class GoodsDetailVO {

    @ApiModelProperty("商品基本信息")
    private GoodsInfo goodsInfo;

    @ApiModelProperty("商品属性列表")
    private List<Attribute> attributeList;

    @ApiModelProperty("商品规格列表")
    private List<Specification> specList;

    @ApiModelProperty("商品库存单元列表")
    private List<Sku> skuList;

    @Data
    @ApiModel("商品信息")
    public static class GoodsInfo {

        @ApiModelProperty("商品ID")
        private Long id;

        @ApiModelProperty("商品名称")
        private String name;

        @ApiModelProperty("商品原价（单位：分）")
        private Long originPrice;

        @ApiModelProperty("商品零售价（单位：分）")
        private Long price;

        @ApiModelProperty("销量")
        private Integer sales;

        @ApiModelProperty("商品图册")
        private List<String> album;

        @ApiModelProperty("商品详情")
        private String detail;
    }


    @Data
    @ApiModel("属性信息")
    public static class Attribute {
        @ApiModelProperty("属性ID")
        private Long id;
        @ApiModelProperty("属性名称")
        private String name;
        @ApiModelProperty("属性值")
        private String value;
    }

    @Data
    @ApiModel("规格信息")
    public static class Specification {

        @ApiModelProperty(value = "规格名称", example = "颜色")
        private String name;

        @ApiModelProperty(value = "规格项列表", example = "黑,白")
        private List<Value> values;

        @Data
        @ApiModel("规格项")
        public static class Value {
            @ApiModelProperty("规格项ID")
            private Long id;

            @ApiModelProperty("规格项值")
            private String value;
        }
    }

    @Data
    @ApiModel("商品库存单元")
    public static class Sku {
        @ApiModelProperty("库存单元ID")
        private Long id;

        @ApiModelProperty("库存单元名称")
        private String name;

        @ApiModelProperty("库存单元规格值ID集合，以英文逗号拼接")
        private String specIds;

        @ApiModelProperty("价格")
        private Long price;

        @ApiModelProperty("库存")
        private Integer stockNum;

        @ApiModelProperty("商品图片URL")
        private String picUrl;

    }
}
