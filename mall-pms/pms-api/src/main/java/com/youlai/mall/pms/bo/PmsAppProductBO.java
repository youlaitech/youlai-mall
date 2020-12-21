package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsAttribute;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpecification;
import com.youlai.mall.pms.pojo.dto.PmsSpuDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmsAppProductBO {

    private PmsSpuDTO spu;

    private List<PmsSku> skuList;

    private List<PmsAttribute> attributes;

    private List<PmsSpecification> specifications;

}
