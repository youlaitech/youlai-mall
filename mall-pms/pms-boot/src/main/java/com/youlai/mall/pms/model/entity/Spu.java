package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * SPU 实体类
 *
 * @author Ray Hao
 * @since 2022/7/2
 */
@TableName("pms_spu")
@Data
@Accessors(chain = true)
public class Spu extends BaseEntity {

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    private Integer sales;

    private String imgUrl;

    private String[] album;

    private String unit;

    private String description;

    private String detail;

    private Integer status;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String brandName;

    @TableField(exist = false)
    private List<Sku> skuList;
}
