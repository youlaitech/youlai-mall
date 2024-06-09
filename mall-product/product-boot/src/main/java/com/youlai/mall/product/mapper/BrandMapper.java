package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.product.model.entity.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

    List<Brand> listBrandOptions(Long categoryId);
}
