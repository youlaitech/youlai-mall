package com.youlai.mall.pms.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpu;
import com.youlai.mall.pms.search.service.IPmsSearchService;
import com.youlai.mall.pms.search.utils.ElasticsearchConvertUtils;
import com.youlai.mall.pms.search.utils.ElasticsearchUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.youlai.common.core.constant.EsIndexContstants.PMS_SPU;

@Service
@AllArgsConstructor
@Slf4j
public class PmsSearchServiceImpl implements IPmsSearchService {

    private  ElasticsearchUtils elasticsearchUtils;

    @Override
    public PmsSpu searchSpuById(String id) throws IOException {
        log.info("根据文档id查询商品信息,id:{}", id);
        return elasticsearchUtils.findById(PMS_SPU, id, PmsSpu.class);
    }

    @Override
    public List<PmsSpu> searchSpuByKey(String key) throws IOException {
        log.info("根据商品名称查询商品信息,name:{}", key);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("name", key));
        SearchHits<PmsSpu> searchHits = elasticsearchUtils.search(PMS_SPU, PmsSpu.class, builder.query());
        return ElasticsearchConvertUtils.searchHitsConvertDataList(searchHits);
    }
}
