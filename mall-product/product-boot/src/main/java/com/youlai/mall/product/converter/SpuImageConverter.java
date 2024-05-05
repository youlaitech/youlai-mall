package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.entity.SpuImage;
import com.youlai.mall.product.model.vo.SpuImagePageVO;
import com.youlai.mall.product.model.form.SpuImageForm;
import com.youlai.mall.product.model.bo.SpuImageBO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpuImageConverter{

    SpuImagePageVO bo2PageVo(SpuImageBO bo);

    Page<SpuImagePageVO> bo2PageVo(Page<SpuImageBO> bo);

    SpuImageForm entity2Form(SpuImage entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SpuImage form2Entity(SpuImageForm entity);


    SpuImage formImage2Entity(SpuForm.Image formImages);

    List<SpuImage> formImage2Entity(List<SpuForm.Image> formImages);
}