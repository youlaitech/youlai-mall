package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.CategoryAttrEntity;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttrVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    AttrForm toForm(CategoryAttrEntity entity);

    List<CategoryAttrEntity> toForm(List<CategoryAttrEntity> entity);

    CategoryAttrEntity toEntity(AttrForm attr);

    List<CategoryAttrEntity> toEntity(List<AttrForm> attrs);

    AttrVO toVO(AttrBO attr);

    List<AttrVO> toVO(List<AttrBO> attrs);


}