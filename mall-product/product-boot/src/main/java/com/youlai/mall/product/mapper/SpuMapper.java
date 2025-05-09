package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.entity.SpuEntity;
import com.youlai.mall.product.model.query.ProductPageQuery;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.client.ClientSpuPageVO;
import com.youlai.mall.product.model.vo.SpuPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  商品SPU持久化接口
 */
@Mapper
public interface SpuMapper extends BaseMapper<SpuEntity> {

    /**
     * Admin-商品分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 商品分页列表
     */
    List<SpuPageVO> getSpuPage(Page<SpuPageVO> page, SpuPageQuery queryParams);

    /**
     * 客户端-商品分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 商品分页列表
     */
    List<ClientSpuPageVO> getClientSpuPage(Page<ClientSpuPageVO> page, ProductPageQuery queryParams);


}
