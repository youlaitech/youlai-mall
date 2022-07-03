package com.youlai.mall.pms.pojo.vo;

import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 【管理端】商品详情视图对象
 *
 * @author haoxr
 * @date 2022/3/13
 */
@Data
@ApiModel("商品详情视图对象")
public class PmsSpuDetailVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    @ApiModelProperty("商品主图")
    private String picUrl;

    @ApiModelProperty("商品副图")
    private String[] subPicUrls;

    private String description;

    private String detail;

    private List<PmsSpuAttribute> attrList;

    private List<PmsSpuAttribute> specList;

    private List<PmsSku> skuList;

}
