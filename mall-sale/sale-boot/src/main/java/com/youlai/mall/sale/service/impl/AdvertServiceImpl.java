package com.youlai.mall.sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.enums.StatusEnum;
import com.youlai.mall.sale.converter.AdvertConverter;
import com.youlai.mall.sale.model.entity.Advert;
import com.youlai.mall.sale.mapper.AdvertMapper;
import com.youlai.mall.sale.model.query.AdvertPageQuery;
import com.youlai.mall.sale.model.vo.BannerVO;
import com.youlai.mall.sale.model.vo.AdvertPageVO;
import com.youlai.mall.sale.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告业务实现类
 *
 * @author Ray.Hao
 * @since 2022/5/28
 */
@Service
@RequiredArgsConstructor
public class AdvertServiceImpl extends ServiceImpl<AdvertMapper, Advert> implements AdvertService {

    private final AdvertConverter advertConverter;

    /**
     * 广告分页列表
     *
     * @param queryParams 查询参数
     * @return 广告分页列表
     */
    @Override
    public Page<AdvertPageVO> getAdvertPage(AdvertPageQuery queryParams) {
        Page<Advert> page = this.baseMapper.getAdvertPage(new Page<>(queryParams.getPageNum(),
                        queryParams.getPageSize()),
                queryParams);
        return advertConverter.toPageVo(page);
    }

    /**
     * 获取广告横幅列表
     */
    @Override
    public List<BannerVO> getBannerList() {

        List<Advert> entities = this.list(new LambdaQueryWrapper<Advert>().
                eq(Advert::getStatus, StatusEnum.ENABLE.getValue())
                .select(Advert::getTitle, Advert::getImageUrl, Advert::getRedirectUrl)
        );
        return advertConverter.entity2BannerVo(entities);
    }
}
