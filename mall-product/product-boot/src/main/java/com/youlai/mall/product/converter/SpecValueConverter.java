package com.youlai.mall.product.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.vo.SpecValuePageVO;
import com.youlai.mall.product.model.bo.SpuSpecValueBO;

@Mapper(componentModel = "spring")
public interface SpecValueConverter{

    SpecValuePageVO bo2PageVo(SpuSpecValueBO bo);

    Page<SpecValuePageVO> bo2PageVo(Page<SpuSpecValueBO> bo);

}