package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsAttrValue;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpecCategory;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SpuDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppProductBO {

    private SpuDTO spu;

    private List<SkuDTO> skuList;

    private List<PmsAttrValue> attributes;

    private List<PmsSpecCategory> specifications;

}
