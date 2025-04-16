package com.youlai.mall.product.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.SpuEntity;
import com.youlai.mall.product.model.query.ProductPageQuery;
import com.youlai.mall.product.model.vo.ProductDetailVO;
import com.youlai.mall.product.model.vo.ProductPageVO;

/**
 * 商品服务
 *
 * @author Ray.Hao
 * @since  2022/2/5
 */
public interface ProductService extends IService<SpuEntity> {

    /**
     * 商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<ProductPageVO>
     */
    IPage<ProductPageVO> getProductPage(ProductPageQuery queryParams);

    /**
     * 获取商品详情
     *
     * @param id SPU ID
     * @return 商品详情
     */
    ProductDetailVO getProductDetail(Long id);
}
