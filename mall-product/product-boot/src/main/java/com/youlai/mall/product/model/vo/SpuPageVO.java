package com.youlai.mall.product.model.vo;

import com.youlai.mall.product.model.entity.Sku;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 【管理端】商品分页视图对象
 *
 * @author haoxr
 * @since 2022/3/13
 */
@Data
@Accessors(chain = true)
public class SpuPageVO {

    private Long id;

    private String name;

    private String imgUrl;

    private String unit;

    private String description;

    private Integer status;

    private String categoryName;

    private String brandName;
}
