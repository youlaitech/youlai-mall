package com.fly4j.yshop.pms.pojo.dto.app;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author haoxianrui
 * @since 2020-04-20
 **/
@Data
@ApiModel
@Accessors
public class AppSkuDTO {

    private List<Tree> tree;

    @Data
    public static class Tree{
        private String k;
        private List<V> v;
        private String k_s;
    }

    @Data
    public static class V{
        private String id;
        private String name;
        private String imgUrl;
        private String previewImgUrl;
    }

    public List<Sku> list;

    public static class Sku{
        private Long id;

        private BigDecimal price;

        private String s1;

        private String s2;

        private String s3;

        private Integer stock_num;
    }

    // 默认价格（单位元）
    private String price;
    private Integer stock_num;
    // 无规格商品 skuId 取 collection_id，否则取所选 sku 组合对应的 id
    private Long collection_id;
    // 是否无规格商品
    private Boolean none_sku;

}
