package com.youlai.mall.product.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品分类 APP 端VO
 *
 * @author Ray.Hao
 * @since 2024/5/21
 */
@Data
public class CategoryAppVO {

    private String catId;

    private String catName;

    private Integer catType;

    private Boolean showPic;

    private Boolean showVideo;

    private List<SecondLevelCategory> children;

    @Data
    public static class SecondLevelCategory {

        private String catId;

        private String catName;

        private Integer catType;

        private Boolean showPic;

        private Boolean showVideo;

        private List<ThirdLevelCategory> childCateList;

    }

    @Data
    public static class ThirdLevelCategory {

        private String catId;

        private String catName;

        private Boolean showPic;

        private Boolean showVideo;

        private String backImg;
    }

}
