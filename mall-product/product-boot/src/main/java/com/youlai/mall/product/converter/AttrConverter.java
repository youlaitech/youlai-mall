package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.form.AttrForm;
import org.mapstruct.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.vo.AttributePageVO;
import com.youlai.mall.product.model.bo.AttrBO;

@Mapper(componentModel = "spring")
public interface AttrConverter {

    @Mappings({
            @Mapping(target = "inputTypeLabel", expression = "java(bo.getInputType()!=null? bo.getInputType().getLabel():\"\")")
    })
    AttributePageVO convertToPageVo(AttrBO bo);

    Page<AttributePageVO> convertToPageVo(Page<AttrBO> bo);

    AttrForm convertToForm(Attr entity);

    Attr toEntity(AttrForm entity);
}