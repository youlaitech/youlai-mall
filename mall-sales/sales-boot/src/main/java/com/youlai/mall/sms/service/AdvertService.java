package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.model.entity.Advert;
import com.youlai.mall.sms.model.query.AdvertPageQuery;
import com.youlai.mall.sms.model.vo.BannerVO;
import com.youlai.mall.sms.model.vo.AdvertPageVO;

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
