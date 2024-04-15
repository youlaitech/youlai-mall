package com.youlai.mall.pms.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.pms.model.dto.SkuSpecValueDTO;
import com.youlai.mall.pms.model.entity.SkuSpecValue;
import com.youlai.mall.pms.model.vo.SkuSpecValuePageVO;
import com.youlai.mall.pms.model.form.SkuSpecValueForm;
import com.youlai.mall.pms.model.bo.SkuSpecValueBO;

@Mapper(componentModel = "spring")
public interface SkuSpecValueConverter{

    SkuSpecValuePageVO bo2PageVo(SkuSpecValueBO bo);

    Page<SkuSpecValuePageVO> bo2PageVo(Page<SkuSpecValueBO> bo);

    SkuSpecValueForm entity2Form(SkuSpecValue entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SkuSpecValue form2Entity(SkuSpecValueForm entity);
}