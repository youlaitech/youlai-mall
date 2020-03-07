package com.fly.shop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 商品品牌
 * @author: Mr.
 * @create: 2020-03-05 13:10
 **/

@Data
@TableName("pms_brand")
@Accessors(chain = true)
public class PmsBrand extends BaseEntity {
    private Long id;
    private String name;
    private String firstLetter; // 首字母
    private Integer sort;
    private Integer factoryStatus;  // 是否为品牌制造商：0->不是；1->是
    private Integer showStatus;
    private Integer productCount;   // 产品数量
    private Integer productCommentCount;    // 产品评论数量
    private String logo;    // 品牌logo
    private String bigPic;  // 专区大图
    private String brandStory;  // 品牌故事
}
