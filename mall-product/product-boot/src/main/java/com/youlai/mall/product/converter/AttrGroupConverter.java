package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.AttrGroupBO;
import com.youlai.mall.product.model.entity.AttrGroup;
import com.youlai.mall.product.model.form.AttrGroupForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.vo.AttributeGroupPageVO;

@Mapper(componentModel = "spring")
public interface AttrGroupConverter {

    AttributeGroupPageVO toPageVo(AttrGroupBO bo);

    Page<AttributeGroupPageVO> toPageVo(Page<AttrGroupBO> bo);

    AttrGroupForm toForm(AttrGroup entity);

    @InheritInverseConfiguration(name = "toForm")
    AttrGroup toEntity(AttrGroupForm entity);
}