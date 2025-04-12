package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.AttrEntity;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttrVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    AttrForm toForm(AttrEntity entity);

    List<AttrEntity> toForm(List<AttrEntity> entity);

    AttrEntity toEntity(AttrForm attr);

    List<AttrEntity> toEntity(List<AttrForm> attrs);

    AttrVO toVO(AttrBO attr);

    List<AttrVO> toVO(List<AttrBO> attrs);


}