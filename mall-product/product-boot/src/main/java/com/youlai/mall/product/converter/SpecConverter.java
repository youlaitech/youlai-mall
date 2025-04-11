package com.youlai.mall.product.converter;

import com.youlai.mall.product.model.vo.SpecVO;
import org.mapstruct.Mapper;

import com.youlai.mall.product.model.entity.Spec;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.bo.SpecBO;

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

    SpecVO toVO(SpecBO spec);

    List<SpecVO> toVO(List<SpecBO> specs);
}