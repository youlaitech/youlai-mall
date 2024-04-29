package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.form.SpuForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.vo.SpecPageVO;
import com.youlai.mall.pms.model.bo.SpuSpecBO;

@Mapper(componentModel = "spring")
public interface SpecConverter{

    SpecPageVO bo2PageVo(SpuSpecBO bo);

    Page<SpecPageVO> bo2PageVo(Page<SpuSpecBO> bo);

    SpuForm.SpuSpec entity2Form(SpuSpec entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SpuSpec form2Entity(SpuForm.SpuSpec formData);

}