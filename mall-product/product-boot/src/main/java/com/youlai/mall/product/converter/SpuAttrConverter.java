package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.bo.SpuAttrBO;
import com.youlai.mall.product.model.form.SpuForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 商品属性对象转换器
 *
 * @author Ray.Hao
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface SpuAttrConverter {

    @Mappings({
            @Mapping(target = "attrName", source = "name"),
            @Mapping(target = "attrValue", source = "value"),
    })
    SpuForm.Attr toSpuFormAttr(SpuAttrBO bo);

    List<SpuForm.Attr> toSpuFormAttr(List<SpuAttrBO> boList);
}
