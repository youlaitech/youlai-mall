package com.youlai.mall.product.model.vo.client;

import lombok.Data;

import java.util.List;

/**
 * 客户端分类视图对象
 *
 * @author Ray.Hao
 * @since 2024/5/21
 */
@Data
public class ClientCategoryVO {

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
