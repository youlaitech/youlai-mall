package com.youlai.mall.product.converter;

import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.model.entity.BrandCategory;
import com.youlai.mall.product.model.form.BrandCategoryForm;
import com.youlai.mall.product.model.vo.BrandCategoryVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 品牌转换器
 *
 * @author Ray Hao
 * @since 2024/5/8
 */
@Mapper(componentModel = "spring")
public interface BrandConverter {

    List<Option<Long>> convertToOptions(Brand entity);

}