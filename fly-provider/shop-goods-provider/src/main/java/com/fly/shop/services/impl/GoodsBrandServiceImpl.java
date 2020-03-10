package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.GoodsBrandMapper;
import com.fly.shop.pojo.entity.GoodsBrand;
import com.fly.shop.services.IGoodsBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 商品的品牌业务
 * @author: Mr.
 * @create: 2020-03-05 16:56
 **/
@Service
public class GoodsBrandServiceImpl extends ServiceImpl<GoodsBrandMapper,GoodsBrand> implements IGoodsBrandService {

    @Resource
    private GoodsBrandMapper goodsBrandMapper;

    @Override
    public List<GoodsBrand> findAll() {
        return goodsBrandMapper.findAll();
    }
}
