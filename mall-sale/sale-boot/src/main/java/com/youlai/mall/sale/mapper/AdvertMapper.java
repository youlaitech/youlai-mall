package com.youlai.mall.sale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sale.model.entity.Advert;
import com.youlai.mall.sale.model.query.AdvertPageQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertMapper extends BaseMapper<Advert> {

    /**
     * 广告分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    Page<Advert> getAdvertPage(Page<Advert> page, AdvertPageQuery queryParams);
}
