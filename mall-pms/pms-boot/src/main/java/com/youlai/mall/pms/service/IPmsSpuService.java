package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import java.util.List;


public interface IPmsSpuService extends IService<PmsSpu> {

    IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu);

    boolean add(com.youlai.mall.pms.pojo.bo.admin.ProductBO productBO);

    com.youlai.mall.pms.pojo.bo.admin.ProductBO getBySpuId(Long id);

    boolean removeBySpuIds(List<Long> spuIds);

    boolean updateById(com.youlai.mall.pms.pojo.bo.admin.ProductBO productBO);

    ProductBO getProductByIdForApp(Long id);
}
