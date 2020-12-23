package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsSpuAttr;
import com.youlai.mall.pms.pojo.PmsCategorySpec;
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

    private List<PmsSpuAttr> attributes;

    private List<PmsCategorySpec> specifications;

}
