package com.youlai.mall.product.converter;

import org.mapstruct.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.entity.Attribute;
import com.youlai.mall.product.model.vo.AttributePageVO;
import com.youlai.mall.product.model.form.AttributeForm;
import com.youlai.mall.product.model.bo.AttributeBO;

@Mapper(componentModel = "spring")
public interface AttributeConverter {

    @Mappings({
            @Mapping(target = "typeLabel", expression = "java(bo.getType()!=null? bo.getType().getLabel():\"\")"),
            @Mapping(target = "inputMethodLabel", expression = "java(bo.getInputMethod()!=null? bo.getInputMethod().getLabel():\"\")")
    })
    AttributePageVO convertToPageVo(AttributeBO bo);

    Page<AttributePageVO> convertToPageVo(Page<AttributeBO> bo);

    AttributeForm convertToForm(Attribute entity);

    Attribute convertToEntity(AttributeForm entity);
}