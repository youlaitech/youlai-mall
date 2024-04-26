package com.youlai.mall.pms.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.entity.Spec;
import com.youlai.mall.pms.model.vo.SpecPageVO;
import com.youlai.mall.pms.model.form.SpecForm;
import com.youlai.mall.pms.model.bo.SpuSpecBO;

@Mapper(componentModel = "spring")
public interface SpecConverter{

    SpecPageVO bo2PageVo(SpuSpecBO bo);

    Page<SpecPageVO> bo2PageVo(Page<SpuSpecBO> bo);

    SpecForm entity2Form(Spec entity);

    @InheritInverseConfiguration(name = "entity2Form")
    Spec form2Entity(SpecForm entity);
}