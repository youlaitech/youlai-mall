package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsSpu;


public interface ISpuService extends IService<PmsSpu> {

    ProductBO getProductById(Long id);
}
