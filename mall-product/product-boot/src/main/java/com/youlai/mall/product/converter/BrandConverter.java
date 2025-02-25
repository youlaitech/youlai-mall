package com.youlai.mall.product.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.model.vo.BrandPageVO;
import org.mapstruct.*;

import java.util.List;

/**
 * 品牌转换器
 *
 * @author Ray.Hao
 * @since 2024/5/8
 */
@Mapper(componentModel = "spring")
public interface BrandConverter {

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name"),
    })
    Option<Long> convertToOption(Brand entity);

    List<Option<Long>> convertToOption(List<Brand> list);

    BrandPageVO convertToVo(Brand entity);

    Page<BrandPageVO> toPageVo(Page<Brand> page);
}