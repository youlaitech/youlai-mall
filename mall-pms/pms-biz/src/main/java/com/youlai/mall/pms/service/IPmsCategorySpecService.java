package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.PmsCategorySpec;

import java.util.List;

public interface IPmsCategorySpecService extends IService<PmsCategorySpec> {

    List<PmsCategorySpec> listBySpuId(Long spuId);

}