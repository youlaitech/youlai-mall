package com.youlai.mall.pms.model.form;

import com.youlai.mall.pms.model.entity.Sku;
import lombok.Data;
import java.util.List;

/**
 * 商品SPU表单对象
 *
 * @author haoxr
 * @since  2022/7/2
 */
@Data
public class PmsSpuForm {

    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private Long originPrice;
    private Long price;
    private String imgUrl;
    private String[] subImgUrls;
    private String description;
    private String detail;

    private List<PmsSpuAttributeForm> attrList;

    private List<PmsSpuAttributeForm> specList;

    private List<Sku> skuList;
}
