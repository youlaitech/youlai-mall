package com.fly.shop.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.springframework.core.annotation.Order;

/**
 * @description: 商品品牌
 * @author: Mr.
 * @create: 2020-03-05 13:10
 **/

@Data
@TableName("pms_brand")
@Accessors(chain = true)
public class PmsBrand extends BaseEntity {
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long brandId;
    private String brandName;
    private String firstLetter; // 首字母
    private Integer sort;
    private Integer factoryStatus;  // 是否为品牌制造商：0->不是；1->是
    private Integer showStatus;
    private Integer productCount;   // 产品数量
    private Integer productCommentCount;    // 产品评论数量
    private String logoUrl;    // 品牌logo
    private String bigPicUrl;  // 专区大图
    private String brandStory;  // 品牌故事
}
