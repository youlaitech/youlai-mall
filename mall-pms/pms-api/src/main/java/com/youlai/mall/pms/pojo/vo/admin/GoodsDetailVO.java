package com.youlai.mall.pms.pojo.vo.admin;

import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 商品详情视图层对象
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Data
@ToString
public class GoodsDetailVO {

    private Long id;

    private String name;

    private Long categoryId;

    private Long brandId;

    private Long originPrice;

    private Long price;

    private String picUrl;

    private String[] subPicUrls;

    private String description;

    private String detail;


    private List<PmsSpuAttributeValue> attrList;

    private List<PmsSpuAttributeValue> specList;

    private List<PmsSku> skuList;

}
