package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.product.model.entity.Sku;
import com.youlai.mall.product.model.entity.SkuSpecValue;
import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 商品对象转换器
 *
 * @author Ray.Hao
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface SkuConverter {

    SkuDTO convertToDto(SkuBO bo);

    List<SkuDTO> convertToDto(List<SkuBO> list);


    Sku toEntity(SpuForm.Sku skuForm);


    SpuForm.Sku toForm(SkuBO bo);
}
