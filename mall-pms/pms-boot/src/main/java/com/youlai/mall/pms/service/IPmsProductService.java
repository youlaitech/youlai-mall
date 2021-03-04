package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.bo.app.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsProduct;
import java.util.List;


public interface IPmsProductService extends IService<PmsProduct> {

    IPage<PmsProduct> list(Page<PmsProduct> page, PmsProduct spu);

    boolean add(com.youlai.mall.pms.bo.admin.ProductBO spuBO);

    com.youlai.mall.pms.bo.admin.ProductBO getBySpuId(Long id);

    boolean removeBySpuIds(List<Long> spuIds);

    boolean updateById(com.youlai.mall.pms.bo.admin.ProductBO spuBO);

    ProductBO getProductByIdForApp(Long id);
}
