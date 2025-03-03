package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    AttrForm toForm(Attr entity);
    List<AttrGroupForm.Attr> toForm(List<Attr> entity);

    Attr convertToEntity(AttrGroupForm.Attr attr);
    List<Attr> convertToEntity(List<AttrGroupForm.Attr> attrs);

}