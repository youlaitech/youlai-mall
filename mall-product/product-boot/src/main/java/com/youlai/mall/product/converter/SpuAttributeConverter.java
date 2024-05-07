package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.entity.SpuAttributeValue;
import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 商品属性对象转换器
 *
 * @author haoxr
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface SpuAttributeConverter {


    SpuAttributeValue formAttribute2Entity(SpuForm.AttributeValue formAttribute);

    List<SpuAttributeValue> formAttribute2Entity(List<SpuForm.AttributeValue> formAttribute);
}
