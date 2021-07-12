package com.youlai.mall.pms.pojo.dto.admin;

import com.youlai.mall.pms.pojo.dto.SpuDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Data
public class ProductFormDTO {
    private SpuDTO spu;

    private List<PmsSpuAttributeValue> attrs;

    private List<PmsSku> skus;


}
