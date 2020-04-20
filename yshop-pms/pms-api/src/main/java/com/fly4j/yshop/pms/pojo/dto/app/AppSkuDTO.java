package com.fly4j.yshop.pms.pojo.dto.app;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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

    public List<Sku> list;

    // 默认价格（单位元）
    private String price;
    private Integer stock_num;
    // 无规格商品 skuId 取 collection_id，否则取所选 sku 组合对应的 id
    private Long collection_id;
    // 是否无规格商品
    private Boolean none_sku;

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



    @Data
    public static class Sku{
        private Long id;

        private BigDecimal price;

        private String s1;

        private String s2;

        private String s3;

        private Integer stock_num;


        public String getS1(){
            if(StringUtils.isNotBlank(s1)){
                return s1;
            }
            return "0";
        }
        public String getS2(){
            if(StringUtils.isNotBlank(s2)){
                return s2;
            }
            return "0";
        }

        public String getS3(){
            if(StringUtils.isNotBlank(s3)){
                return s3;
            }
            return "0";
        }
    }



}
