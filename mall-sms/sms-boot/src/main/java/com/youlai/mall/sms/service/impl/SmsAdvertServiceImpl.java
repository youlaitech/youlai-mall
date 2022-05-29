package com.youlai.mall.sms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sms.pojo.entity.SmsAdvert;
import com.youlai.mall.sms.mapper.SmsAdvertMapper;
import com.youlai.mall.sms.pojo.query.AdvertPageQuery;
import com.youlai.mall.sms.service.SmsAdvertService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告业务实现类
 *
 * @author haoxr
 * @date 2022/5/28
 */
@Service
public class SmsAdvertServiceImpl extends ServiceImpl<SmsAdvertMapper, SmsAdvert> implements SmsAdvertService {

    /**
     * 广告分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<SmsAdvert> listAdvertsPage(AdvertPageQuery queryParams) {
        Page<SmsAdvert> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SmsAdvert> list = this.baseMapper.listAdvertsPage(page, queryParams);
        page.setRecords(list);
        return page;
    }
}
