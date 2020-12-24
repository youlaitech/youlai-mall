package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsSpuAttrValue;
import com.youlai.mall.pms.pojo.PmsSpec;
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

    private List<PmsSpuAttrValue> attributes;

    private List<PmsSpec> specifications;

}
