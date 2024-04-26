package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.entity.SpuAttribute;
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
    SpuAttribute form2Entity(PmsSpuAttributeForm form);

    SpuAttribute formAttribute2Entity(SpuForm.SpuAttribute formAttribute);

    List<SpuAttribute> formAttribute2Entity(List<SpuForm.SpuAttribute> formAttribute);
}
