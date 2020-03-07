package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.PmsBrandMapper;
import com.fly.shop.pojo.entity.PmsBrand;
import com.fly.shop.services.IPmsBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 商品的品牌业务
 * @author: Mr.
 * @create: 2020-03-05 16:56
 **/
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper,PmsBrand> implements IPmsBrandService {

    @Resource
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public List<PmsBrand> findAll() {
        return pmsBrandMapper.findAll();
    }
}
