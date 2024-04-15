package com.youlai.mall.pms.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.dto.AttributeDTO;
import com.youlai.mall.pms.model.entity.Attribute;
import com.youlai.mall.pms.model.vo.AttributePageVO;
import com.youlai.mall.pms.model.form.AttributeForm;
import com.youlai.mall.pms.model.bo.AttributeBO;

@Mapper(componentModel = "spring")
public interface AttributeConverter{

    AttributePageVO bo2PageVo(AttributeBO bo);

    Page<AttributePageVO> bo2PageVo(Page<AttributeBO> bo);

    AttributeForm entity2Form(Attribute entity);

    @InheritInverseConfiguration(name = "entity2Form")
    Attribute form2Entity(AttributeForm entity);
}