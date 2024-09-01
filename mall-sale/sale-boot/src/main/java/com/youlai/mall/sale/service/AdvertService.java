package com.youlai.mall.sale.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sale.model.entity.Advert;
import com.youlai.mall.sale.model.query.AdvertPageQuery;
import com.youlai.mall.sale.model.vo.BannerVO;
import com.youlai.mall.sale.model.vo.AdvertPageVO;

import java.util.List;

public interface AdvertService extends IService<Advert> {

    /**
     * 广告分页列表
     *
     * @param queryParams
     * @return
     */
    Page<AdvertPageVO> getAdvertPage(AdvertPageQuery queryParams);

    List<BannerVO> getBannerList();
}
