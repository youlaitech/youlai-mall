package com.youlai.mall.pms.pojo.dto.app;

import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFormDTO {

    private SpuInfo spuInfo;

    private List<PmsSpuAttributeValue> attrValueList;


    private List<PmsSku> skuList;


    @Data
    public static class SpuInfo {
        private Long id;
        private String name;
        private Long categoryId;
        private Long brandId;
        private Long originPrice;
        private Long price;
        private Integer sales;
        private String picUrl;
        private List<String> album;
        private String unit;
        private String description;
        private String detail;
        private Integer status;
    }

}
