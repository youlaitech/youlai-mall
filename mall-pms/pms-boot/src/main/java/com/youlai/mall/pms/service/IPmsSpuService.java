package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.bo.AppProductBO;
import com.youlai.mall.pms.bo.ProductBO;
import com.youlai.mall.pms.pojo.PmsSpu;
import java.util.List;


public interface IPmsSpuService extends IService<PmsSpu> {

    IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu);

    boolean add(ProductBO spuBO);

    ProductBO getBySpuId(Long id);

    boolean removeBySpuIds(List<Long> spuIds);

    boolean updateById(ProductBO spuBO);

    AppProductBO getProductByIdForApp(Long id);
}
