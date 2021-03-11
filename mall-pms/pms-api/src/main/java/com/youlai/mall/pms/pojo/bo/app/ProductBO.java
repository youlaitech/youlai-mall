package com.youlai.mall.pms.pojo.bo.app;

import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.domain.PmsAttributeValue;
import com.youlai.mall.pms.pojo.domain.PmsSpecification;
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

    private List<PmsSpecification> specs;

    private List<PmsSku> skus;

}
