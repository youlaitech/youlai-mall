package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.Mapper;

import com.youlai.mall.product.model.entity.SpuImage;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpuImageConverter{

    SpuImage convertToEntity(SpuForm.Image formImages);

    List<SpuImage> convertToEntity(List<SpuForm.Image> formImages);
}