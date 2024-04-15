package com.youlai.mall.pms.model.vo;

import com.youlai.mall.pms.model.entity.Sku;
import com.youlai.mall.pms.model.entity.SpuAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 【管理端】商品详情视图对象
 *
 * @author haoxr
 * @since 2022/3/13
 */
@Data
@Schema(description = "商品详情视图对象")
public class PmsSpuDetailVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    @Schema(description="商品主图")
    private String imgUrl;

    @Schema(description="商品副图")
    private String[] subImgUrls;

    private String description;

    private String detail;

    private List<SpuAttribute> attrList;

    private List<SpuAttribute> specList;

    private List<Sku> skuList;

}
