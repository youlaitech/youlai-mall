package com.youlai.mall.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sms.model.entity.SmsAdvert;
import com.youlai.mall.sms.model.query.AdvertPageQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsAdvertMapper extends BaseMapper<SmsAdvert> {

    /**
     * 广告分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    Page<SmsAdvert> listAdvertPages(Page<SmsAdvert> page, AdvertPageQuery queryParams);
}
