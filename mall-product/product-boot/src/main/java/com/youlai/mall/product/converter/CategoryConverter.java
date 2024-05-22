package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.Category;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.CategoryAppVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryConverter {
    CategoryForm convertToForm(Category entity);

    Category convertToEntity(CategoryForm formData);

    @Mappings({
            @Mapping(target = "catId", source = "id"),
            @Mapping(target = "catName", source = "name"),
    })
    CategoryAppVO convertToFirstLevelVo(Category category);

    @Mappings({
            @Mapping(target = "catId", source = "id"),
            @Mapping(target = "catName", source = "name"),
    })
    CategoryAppVO.SecondLevelCategory convertToSecondLevelVo(Category item);


    @Mappings({
            @Mapping(target = "catId", source = "id"),
            @Mapping(target = "catName", source = "name"),
            @Mapping(target = "backImg", source = "iconUrl"),
    })
    CategoryAppVO.ThirdLevelCategory convertToThirdLevelVo(Category item);
}