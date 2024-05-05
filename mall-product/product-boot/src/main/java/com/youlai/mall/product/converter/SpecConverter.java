package com.youlai.mall.product.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.mall.product.model.vo.SpecPageVO;
import com.youlai.mall.product.model.bo.SpuSpecBO;

@Mapper(componentModel = "spring")
public interface SpecConverter{

    SpecPageVO bo2PageVo(SpuSpecBO bo);

    Page<SpecPageVO> bo2PageVo(Page<SpuSpecBO> bo);
}