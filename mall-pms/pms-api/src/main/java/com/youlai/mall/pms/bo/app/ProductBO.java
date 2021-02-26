package com.youlai.mall.pms.bo.app;

import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.domain.PmsSpuAttrValue;
import com.youlai.mall.pms.pojo.domain.PmsCategorySpec;
import com.youlai.mall.pms.pojo.dto.SpuDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBO {

    private SpuDTO spu;

    private List<PmsSpuAttrValue> attrs;

    private List<PmsCategorySpec> specs;

    private List<PmsSku> skuList;

}
