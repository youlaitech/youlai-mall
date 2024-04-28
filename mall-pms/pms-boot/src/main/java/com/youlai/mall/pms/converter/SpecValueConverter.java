package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.entity.SpuSpecValue;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.vo.SpecValuePageVO;
import com.youlai.mall.pms.model.form.SpecValueForm;
import com.youlai.mall.pms.model.bo.SpuSpecValueBO;

@Mapper(componentModel = "spring")
public interface SpecValueConverter{

    SpecValuePageVO bo2PageVo(SpuSpecValueBO bo);

    Page<SpecValuePageVO> bo2PageVo(Page<SpuSpecValueBO> bo);

    SpecValueForm entity2Form(SpuSpecValue entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SpuSpecValue form2Entity(SpecValueForm entity);
}