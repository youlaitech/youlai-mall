package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.entity.PmsSpu;


public interface IProductService extends IService<PmsSpu> {

    ProductBO getProductById(Long id);
}
