package com.youlai.mall.sale.converter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sale.model.entity.Advert;
import com.youlai.mall.sale.model.vo.BannerVO;
import com.youlai.mall.sale.model.vo.AdvertPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 广告实体转换器
 *
 * @author Ray.Hao
 * @since 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface AdvertConverter {

    AdvertPageVO toPageVo(Advert entity);

    Page<AdvertPageVO> toPageVo(Page<Advert> po);

    BannerVO entity2BannerVo(Advert entity);
    
    List<BannerVO> entity2BannerVo(List<Advert> entities);
}