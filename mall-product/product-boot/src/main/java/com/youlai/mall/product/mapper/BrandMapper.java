package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.product.model.entity.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌持久层接口
 *
 * @author Ray.Hao
 * @since 1.0.0
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

    List<Brand> listBrandOptions();
}
