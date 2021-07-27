package com.youlai.mall.pms.pojo.dto.admin;

import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import lombok.Data;
import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Data
public class GoodsFormDTO {

    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private Long originPrice;
    private Long price;
    private String picUrl;
    private List<String> album;
    private String description;
    private String detail;

    private List<PmsSpuAttributeValue> attrValueList;

    private List<PmsSku> skuList;

}
