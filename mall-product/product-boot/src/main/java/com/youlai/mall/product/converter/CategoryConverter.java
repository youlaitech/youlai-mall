package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.CategoryEntity;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.client.ClientCategoryVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryConverter {

    CategoryForm toForm(CategoryEntity entity);

    CategoryEntity toEntity(CategoryForm formData);

    ClientCategoryVO toClientCategoryVO(CategoryEntity categoryEntity);

}