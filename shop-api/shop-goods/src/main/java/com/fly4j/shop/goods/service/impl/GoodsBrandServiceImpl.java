package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsBrandMapper;
import com.fly4j.shop.goods.service.IGoodsBrandService;
import com.fly4j.shop.goods.pojo.entity.GoodsBrand;
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
