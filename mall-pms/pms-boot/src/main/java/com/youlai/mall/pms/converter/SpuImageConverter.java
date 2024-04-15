package com.youlai.mall.pms.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.dto.SpuImageDTO;
import com.youlai.mall.pms.model.entity.SpuImage;
import com.youlai.mall.pms.model.vo.SpuImagePageVO;
import com.youlai.mall.pms.model.form.SpuImageForm;
import com.youlai.mall.pms.model.bo.SpuImageBO;

@Mapper(componentModel = "spring")
public interface SpuImageConverter{

    SpuImagePageVO bo2PageVo(SpuImageBO bo);

    Page<SpuImagePageVO> bo2PageVo(Page<SpuImageBO> bo);

    SpuImageForm entity2Form(SpuImage entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SpuImage form2Entity(SpuImageForm entity);
}