package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.entity.PmsBrand;

import java.util.List;

public interface IPmsBrandService extends IService<PmsBrand>{
    List<PmsBrand> findAll();
}
