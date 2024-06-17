package com.youlai.mall.product.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.entity.Spec;
import com.youlai.mall.product.model.vo.SpecPageVO;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.bo.SpecBO;
import org.mapstruct.Mapping;

/**
 * 规格转换器
 *
 * @author Ray Hao
 * @since 2024-06-13
 */
@Mapper(componentModel = "spring")
public interface SpecConverter {

    @Mapping(target = "inputTypeLabel",
            expression = "java(bo.getInputType()!=null ? bo.getInputType().getLabel(): \"\")"
    )
    SpecPageVO toPageVo(SpecBO bo);

    Page<SpecPageVO> toPageVo(Page<SpecBO> bo);

    SpecForm toForm(Spec entity);

    Spec toEntity(SpecForm formData);
}