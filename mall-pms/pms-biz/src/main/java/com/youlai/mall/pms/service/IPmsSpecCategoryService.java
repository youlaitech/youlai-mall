package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.PmsSpecCategory;

import java.util.List;

public interface IPmsSpecCategoryService extends IService<PmsSpecCategory> {

    List<PmsSpecCategory> listBySpuId(Long spuId);

}