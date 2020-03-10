package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.GoodsAttributeMapper;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.services.IGoodsAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute> implements IGoodsAttributeService {

    @Override
    public Page<GoodsAttribute> selectPage(Page<GoodsAttribute> page, GoodsAttribute goodsAttribute) {
        List<GoodsAttribute> list = this.baseMapper.page(page, goodsAttribute);
        page.setRecords(list);
        return page;
    }
}
