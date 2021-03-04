package com.youlai.mall.pms.search.service;

import com.youlai.mall.pms.pojo.domain.PmsProduct;

import java.io.IOException;
import java.util.List;

public interface IPmsSearchService {

    /**
     * 根据文档id搜索商品
     * @param id
     * @return
     * @throws IOException
     */
    PmsProduct searchSpuById(String id) throws IOException;

    /**
     * 根据商品名称关键字搜索商品
     *
     * @param key 商品名称关键字
     * @return SKU商品集合
     */
    List<PmsProduct> searchSpuByKey(String key) throws IOException;
}
