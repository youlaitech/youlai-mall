package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.model.entity.SpuDetail;
import com.youlai.mall.product.service.SpuDetailService;
import com.youlai.mall.product.mapper.SpuDetailMapper;
import org.springframework.stereotype.Service;

/**
 * 商品详情 服务实现类
 *
 * @author Ray Hao
 * @since 2024/5/8
 */
@Service
public class SpuDetailServiceImpl extends ServiceImpl<SpuDetailMapper, SpuDetail>
        implements SpuDetailService {

    /**
     * 保存商品详情
     *
     * @param spuId  SPU ID
     * @param detail 商品详情
     */

    @Override
    public void saveSpuDetail(Long spuId, String detail) {
        SpuDetail spuDetail = this.getById(spuId);
        if (spuDetail == null) {
            spuDetail = new SpuDetail();
            spuDetail.setSpuId(spuId);
            spuDetail.setDetail(detail);
            this.save(spuDetail);
        } else {
            spuDetail.setDetail(detail);
            this.updateById(spuDetail);
        }
    }
}




