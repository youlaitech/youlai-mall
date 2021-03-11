package com.youlai.mall.pms.pojo.bo.admin;

import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.domain.PmsAttributeValue;
import com.youlai.mall.pms.pojo.domain.PmsSpecificationValue;
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

    private List<PmsAttributeValue> attrValues;

    private List<PmsSpecificationValue> specValues;

    private List<PmsSku> skus;

}
