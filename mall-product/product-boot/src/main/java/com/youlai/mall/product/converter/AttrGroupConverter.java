package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.AttrGroupBO;
import com.youlai.mall.product.model.entity.AttrGroup;
import com.youlai.mall.product.model.form.AttrGroupForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.vo.AttrGroupPageVO;

@Mapper(componentModel = "spring")
public interface AttrGroupConverter {

    AttrGroupPageVO toPageVo(AttrGroupBO bo);

    Page<AttrGroupPageVO> toPageVo(Page<AttrGroupBO> bo);

    AttrGroupForm convertToForm(AttrGroup entity);

    @InheritInverseConfiguration(name = "convertToForm")
    AttrGroup toEntity(AttrGroupForm entity);
}