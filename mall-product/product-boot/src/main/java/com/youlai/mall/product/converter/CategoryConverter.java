package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.CategoryEntity;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.CategoryAppVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryConverter {
    CategoryForm toForm(CategoryEntity entity);

    CategoryEntity toEntity(CategoryForm formData);

    @Mappings({
            @Mapping(target = "catId", source = "id"),
            @Mapping(target = "catName", source = "name"),
    })
    CategoryAppVO convertToFirstLevelVo(CategoryEntity categoryEntity);

    @Mappings({
            @Mapping(target = "catId", source = "id"),
            @Mapping(target = "catName", source = "name"),
    })
    CategoryAppVO.SecondLevelCategory convertToSecondLevelVo(CategoryEntity item);


    @Mappings({
            @Mapping(target = "catId", source = "id"),
            @Mapping(target = "catName", source = "name"),
            @Mapping(target = "backImg", source = "iconUrl"),
    })
    CategoryAppVO.ThirdLevelCategory convertToThirdLevelVo(CategoryEntity item);
}