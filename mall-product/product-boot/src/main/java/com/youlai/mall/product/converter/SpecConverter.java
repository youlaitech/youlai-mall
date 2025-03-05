package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.vo.SpecVO;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.entity.Spec;
import com.youlai.mall.product.model.vo.SpecPageVO;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.bo.SpecBO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 规格转换器
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Mapper(componentModel = "spring")
public interface SpecConverter {

    SpecForm toForm(Spec entity);

    Spec toEntity(SpecForm formData);

    @Mappings({
            @Mapping(
                    target = "inputTypeLabel",
                    expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(spec.getInputType(), com.youlai.mall.product.enums.AttrInputTypeEnum.class))"
            )
    })
    SpecVO toVO(SpecBO spec);

    List<SpecVO> toVO(List<SpecBO> specs);
}