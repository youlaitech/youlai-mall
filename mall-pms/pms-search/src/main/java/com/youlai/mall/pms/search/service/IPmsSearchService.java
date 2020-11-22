package com.youlai.mall.pms.search.service;

import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpu;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IPmsSearchService {

    /**
     * 根据文档id搜索商品
     * @param id
     * @return
     * @throws IOException
     */
    PmsSpu searchSpuById(String id) throws IOException;

    /**
     * 根据商品名称关键字搜索商品
     *
     * @param key 商品名称关键字
     * @return SKU商品集合
     */
    List<PmsSpu> searchSpuByKey(String key) throws IOException;
}
