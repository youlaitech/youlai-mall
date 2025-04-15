package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.SkuSpecBO;
import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkuSpecConverter {

    @Mappings({
            @Mapping(target = "name", source = "specName"),
            @Mapping(target = "value", source = "specValue"),
    })
    SpuForm.SpecValue toSkuFormSpec(SkuSpecBO specBO);

    List<SpuForm.SpecValue> toSkuFormSpec(List<SkuSpecBO> specList);
}