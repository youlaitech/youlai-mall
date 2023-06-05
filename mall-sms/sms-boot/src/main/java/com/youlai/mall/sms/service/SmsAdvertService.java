package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.model.entity.SmsAdvert;
import com.youlai.mall.sms.model.query.AdvertPageQuery;
import com.youlai.mall.sms.model.vo.AdBannerVO;
import com.youlai.mall.sms.model.vo.AdvertPageVO;

import java.util.List;

public interface SmsAdvertService extends IService<SmsAdvert> {

    /**
     * 广告分页列表
     *
     * @param queryParams
     * @return
     */
    Page<AdvertPageVO> listAdvertPages(AdvertPageQuery queryParams);

    List<AdBannerVO> listAdBanners();
}
