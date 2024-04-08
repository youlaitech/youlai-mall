package com.youlai.mall.pms.model.vo;

import com.youlai.mall.pms.model.entity.PmsSku;
import com.youlai.mall.pms.model.entity.PmsSpuAttr;
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
    private String picUrl;

    @Schema(description="商品副图")
    private String[] subPicUrls;

    private String description;

    private String detail;

    private List<PmsSpuAttr> attrList;

    private List<PmsSpuAttr> specList;

    private List<PmsSku> skuList;

}
