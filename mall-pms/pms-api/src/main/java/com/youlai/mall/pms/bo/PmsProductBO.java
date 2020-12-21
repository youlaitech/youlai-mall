package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpecification;
import com.youlai.mall.pms.pojo.PmsAttributeValue;
import com.youlai.mall.pms.pojo.dto.PmsSpuDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsProductBO {

    private PmsSpuDTO spu;

    private List<PmsAttributeValue> attributes;

    private List<PmsSpecification> specifications;

    private List<PmsSku> skuList;

}
