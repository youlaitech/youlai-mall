package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttrVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    AttrForm toForm(Attr entity);

    List<Attr> toForm(List<Attr> entity);

    Attr toEntity(AttrForm attr);

    List<Attr> toEntity(List<AttrForm> attrs);

    AttrVO toVO(AttrBO attr);

    List<AttrVO> toVO(List<AttrBO> attrs);


}