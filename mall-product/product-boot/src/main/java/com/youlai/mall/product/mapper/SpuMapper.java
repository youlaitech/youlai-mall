package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.PmsSpuPageVO;
import com.youlai.mall.product.model.vo.SpuPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * Admin-商品分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 商品分页列表
     */
    List<PmsSpuPageVO> listPagedSpu(Page<PmsSpuPageVO> page, SpuPageQuery queryParams);

    /**
     * APP-商品分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 商品分页列表
     */
    List<SpuPageVO> listPagedSpuForApp(Page<SpuPageVO> page, SpuPageQuery queryParams);


}
