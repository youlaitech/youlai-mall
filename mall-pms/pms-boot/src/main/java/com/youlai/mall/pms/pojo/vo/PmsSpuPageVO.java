package com.youlai.mall.pms.pojo.vo;

import com.youlai.mall.pms.pojo.entity.PmsSku;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 【管理端】商品分页视图对象
 *
 * @author haoxr
 * @date 2022/3/13
 */
@Data
@Accessors(chain = true)
public class PmsSpuPageVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    private Integer sales;

    private String picUrl;

    private String[] album;

    private String unit;

    private String description;

    private String detail;

    private Integer status;

    private String categoryName;

    private String brandName;

    private List<PmsSku> skuList;
}
