package com.youlai.mall.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
public class PmsSpu extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private BigDecimal originPrice;
    private BigDecimal price;
    private Integer sale;
    private String pic;
    private String album;
    private String unit;
    private String description;
    private String detail;
    private Integer status;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String brandName;

    @TableField(exist = false)
    private List<PmsSku> skuList;
}
