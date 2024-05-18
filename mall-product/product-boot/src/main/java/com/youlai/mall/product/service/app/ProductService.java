package com.youlai.mall.product.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.SpuDetailVO;
import com.youlai.mall.product.model.vo.SpuPageVO;

/**
 * 商品服务
 *
 * @author haoxr
 * @since  2022/2/5
 */
public interface ProductService extends IService<Spu> {

    /**
     * 「应用端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams);


    /**
     * 「应用端」获取商品详情
     *
     * @param spuId
     * @return
     */
    SpuDetailVO getSpuDetailForApp(Long spuId);


}
