package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * 商品对象转换器
 *
 * @author Ray Hao
 * @since 2024/4/24
 */
@Mapper(componentModel = "spring")
public interface SpuConverter {

    Spu convertToEntity(SpuForm form);

    @InheritInverseConfiguration(name="convertToEntity")
    SpuForm entity2Form(Spu entity);

}
