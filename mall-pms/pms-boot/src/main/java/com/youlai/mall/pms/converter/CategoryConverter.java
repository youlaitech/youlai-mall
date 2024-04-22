package com.youlai.mall.pms.converter;

import com.youlai.mall.pms.model.entity.Category;
import com.youlai.mall.pms.model.form.CategoryForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryConverter {
    CategoryForm entity2Form(Category entity);

    @InheritInverseConfiguration(name = "entity2Form")
    Category form2Entity(CategoryForm formData);
}