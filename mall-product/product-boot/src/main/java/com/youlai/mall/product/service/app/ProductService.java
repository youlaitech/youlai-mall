package com.youlai.mall.product.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.query.ProductPageQuery;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.ProductDetailVO;
import com.youlai.mall.product.model.vo.SpuDetailVO;
import com.youlai.mall.product.model.vo.ProductPageVO;

/**
 * 商品服务
 *
 * @author Ray
 * @since  2022/2/5
 */
public interface ProductService extends IService<Spu> {

    /**
     * 商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<ProductPageVO>
     */
    IPage<ProductPageVO> listPagedProducts(ProductPageQuery queryParams);

    /**
     * 获取商品详情
     *
     * @param id SPU ID
     * @return 商品详情
     */
    ProductDetailVO getProductDetail(Long id);
}
