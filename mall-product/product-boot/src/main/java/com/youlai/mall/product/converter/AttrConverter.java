package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.model.vo.AttrVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    AttrForm convertToForm(Attr entity);
    List<AttrGroupForm.Attr> convertToForm(List<Attr> entity);

    Attr convertToEntity(AttrGroupForm.Attr attr);
    List<Attr> convertToEntity(List<AttrGroupForm.Attr> attrs);

}