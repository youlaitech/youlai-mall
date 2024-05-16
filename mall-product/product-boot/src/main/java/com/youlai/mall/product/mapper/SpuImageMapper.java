package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.SpuImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.bo.SpuImageBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品图片 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024-04-14
 */

@Mapper
public interface SpuImageMapper extends BaseMapper<SpuImage> {


}
