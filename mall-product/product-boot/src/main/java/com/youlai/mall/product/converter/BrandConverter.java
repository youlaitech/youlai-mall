package com.youlai.mall.product.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.entity.BrandEntity;
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
    Option<Long> toOption(BrandEntity entity);

    List<Option<Long>> toOption(List<BrandEntity> list);

    BrandPageVO convertToVo(BrandEntity entity);

    Page<BrandPageVO> toPageVo(Page<BrandEntity> page);
}