package com.youlai.mall.pms.converter;

import org.mapstruct.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.dto.AttributeDTO;
import com.youlai.mall.pms.model.entity.Attribute;
import com.youlai.mall.pms.model.vo.AttributePageVO;
import com.youlai.mall.pms.model.form.AttributeForm;
import com.youlai.mall.pms.model.bo.AttributeBO;

@Mapper(componentModel = "spring")
public interface AttributeConverter{

    @Mappings({
            @Mapping(target = "inputTypeLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(bo.getInputType(), com.youlai.mall.pms.enums.AttributeInputTypeEnum.class))")
    })
    AttributePageVO bo2PageVo(AttributeBO bo);

    Page<AttributePageVO> bo2PageVo(Page<AttributeBO> bo);

    AttributeForm entity2Form(Attribute entity);

    @InheritInverseConfiguration(name = "entity2Form")
    Attribute form2Entity(AttributeForm entity);
}