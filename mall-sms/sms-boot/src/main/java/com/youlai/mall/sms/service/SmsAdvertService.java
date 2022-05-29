package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.entity.SmsAdvert;
import com.youlai.mall.sms.pojo.query.AdvertPageQuery;

public interface SmsAdvertService extends IService<SmsAdvert> {

    /**
     * 广告分页列表
     *
     * @param queryParams
     * @return
     */
    Page<SmsAdvert> listAdvertsPage(AdvertPageQuery queryParams);
}
