package com.youlai.mall.sms.converter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sms.pojo.entity.SmsAdvert;
import com.youlai.mall.sms.pojo.vo.AdBannerVO;
import com.youlai.mall.sms.pojo.vo.AdvertPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * advert实体转换器
 *
 * @author haoxr
 * @date 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface AdvertConverter {

    AdvertPageVO entity2PageVo(SmsAdvert entity);

    Page<AdvertPageVO> entity2PageVo(Page<SmsAdvert> po);

    AdBannerVO entity2BannerVo(SmsAdvert entity);
    
    List<AdBannerVO> entity2BannerVo(List<SmsAdvert> entities);
}