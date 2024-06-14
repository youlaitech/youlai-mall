package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    AttrForm toForm(Attr entity);

    Attr toEntity(AttrForm entity);
}