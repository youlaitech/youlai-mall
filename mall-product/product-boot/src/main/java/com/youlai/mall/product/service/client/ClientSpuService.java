package com.youlai.mall.product.service.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.SpuEntity;
import com.youlai.mall.product.model.query.client.ClientGoodsPageQuery;
import com.youlai.mall.product.model.vo.client.ClientGoodsDetailVO;
import com.youlai.mall.product.model.vo.client.ClientGoodsPageVO;

public interface ClientSpuService extends IService<SpuEntity> {

    IPage<ClientGoodsPageVO> getGoodsPage(ClientGoodsPageQuery queryParams);

    ClientGoodsDetailVO getGoodsDetail(Long spuId);

}
