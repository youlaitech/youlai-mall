package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.dto.app.ProductFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsSpu;


public interface IProductService extends IService<PmsSpu> {

    ProductFormDTO getProductById(Long id);
}
