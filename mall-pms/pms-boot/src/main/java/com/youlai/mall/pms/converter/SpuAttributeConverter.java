package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.entity.SpuAttributeValue;
import com.youlai.mall.pms.model.form.PmsSpuAttributeForm;
import com.youlai.mall.pms.model.form.SpuForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 商品属性对象转换器
 *
 * @author haoxr
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface SpuAttributeConverter {

    @Mappings({
            @Mapping(target = "id",ignore = true)
    })
    SpuAttributeValue form2Entity(PmsSpuAttributeForm form);

    SpuAttributeValue formAttribute2Entity(SpuForm.Attribute formAttribute);

    List<SpuAttributeValue> formAttribute2Entity(List<SpuForm.Attribute> formAttribute);
}
