package com.youlai.mall.pms.search.service.impl;

import com.youlai.mall.pms.pojo.domain.PmsProduct;
import com.youlai.mall.pms.search.service.IPmsSearchService;
import com.youlai.mall.pms.search.utils.ElasticsearchConvertUtils;
import com.youlai.mall.pms.search.utils.ElasticsearchUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.youlai.common.constant.EsIndexContstants.PMS_SPU;

@Service
@AllArgsConstructor
@Slf4j
public class PmsSearchServiceImpl implements IPmsSearchService {

    private  ElasticsearchUtils elasticsearchUtils;

    @Override
    public PmsProduct searchSpuById(String id) throws IOException {
        log.info("根据文档id查询商品信息,id:{}", id);
        return elasticsearchUtils.findById(PMS_SPU, id, PmsProduct.class);
    }

    @Override
    public List<PmsProduct> searchSpuByKey(String key) throws IOException {
        log.info("根据商品名称查询商品信息,name:{}", key);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("name", key));
        SearchHits<PmsProduct> searchHits = elasticsearchUtils.search(PMS_SPU, PmsProduct.class, builder.query());
        return ElasticsearchConvertUtils.searchHitsConvertDataList(searchHits);
    }
}
