package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.bo.PmsAppSpuBO;
import com.youlai.mall.pms.bo.PmsSpuBO;
import com.youlai.mall.pms.pojo.PmsSpu;

import java.util.List;


public interface IPmsSpuService extends IService<PmsSpu> {

    IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu);

    boolean add(PmsSpuBO spuBO);

    PmsSpuBO getSpuById(Long id);

    boolean removeBySpuIds(List<Long> spuIds);

    boolean updateById(PmsSpuBO spuBO);

    PmsAppSpuBO getAppSpuById(Long id);
}
