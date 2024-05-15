package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Category;
import com.youlai.mall.product.model.form.CategoryForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryConverter {
    CategoryForm convertToForm(Category entity);

    Category convertToEntity(CategoryForm formData);
}