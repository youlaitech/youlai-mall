package com.fly.shop.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
* @Description: TODO
* @author: Mr.
* @create: 2020/3/13 10:06
**/

@Data
@TableName("goods")
public class Goods  extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long brandId;

    private Long goodsCategoryId;

    private String goodsCategoryName;

    private Long feightTemplateId;

    private Long goodsAttributeCategoryId;

    private String name;

    private String pic;

    private String goodsSn;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus;

    /**
     * 上架状态：0->下架；1->上架
     */
    private Integer publishStatus;

    /**
     * 新品状态:0->不是新品；1->新品
     */
    private Integer newStatus;

    /**
     * 推荐状态；0->不推荐；1->推荐
     */
    private Integer recommendStatus;

    /**
     * 审核状态：0->未审核；1->审核通过
     */
    private Integer verifyStatus;

    private Integer sort;

    private Integer sale;

    private BigDecimal price;

    private BigDecimal promotionPrice;

    private Integer giftGrowth;

    private Integer giftPoint;

    private Integer usePointLimit;

    private String subTitle;

    private BigDecimal originalPrice;

    private Integer stock;

    private Integer lowStock;


    private String unit;

    private BigDecimal weight;

    /**
     * 是否为预告商品：0->不是；1->是
     */
    private Integer previewStatus;

    /**
     * 以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮
     */
    private String serviceIds;

    private String keywords;

    private String note;

    private String albumPics;

    private String detailTitle;

    private Date promotionStartTime;

    private Date promotionEndTime;

    private Integer promotionPerLimit;

    /**
     * 促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购
     */
    private Integer promotionType;

    private String brandName;

    private String description;

    private String detailDesc;

    private String detailHtml;

    private String detailMobileHtml;

}