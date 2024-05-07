package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.BrandCategory;
import com.youlai.mall.product.model.form.BrandCategoryForm;
import com.youlai.mall.product.model.vo.BrandCategoryVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 品牌分类关联转换器
 *
 * @author Ray Hao
 * @since 2024-05-06
 */
@Mapper(componentModel = "spring")
public interface BrandCategoryConverter {

    List<BrandCategoryVO> entity2Vo(List<BrandCategory> list);

    BrandCategoryForm entity2Form(BrandCategory entity);

    @InheritInverseConfiguration(name = "entity2Form")
    BrandCategory form2Entity(BrandCategoryForm entity);

}