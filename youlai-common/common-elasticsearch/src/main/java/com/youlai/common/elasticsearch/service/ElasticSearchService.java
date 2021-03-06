package com.youlai.common.elasticsearch.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.common.elasticsearch.constant.EsConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hxr
 * @date 2021-03-05
 */
@Service
@AllArgsConstructor

public class ElasticSearchService{

    private RestHighLevelClient client;



    @SneakyThrows
    public long count(QueryBuilder queryBuilder,String... indices){

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        CountRequest countRequest=new CountRequest(indices);
        countRequest.source(searchSourceBuilder);

        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        long count = countResponse.getCount();
        return count;
    }


    @SneakyThrows
    public <T> List<T> search(QueryBuilder queryBuilder, Class<T> clazz, String... indices) {
        // 构造请求参数
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(EsConstants.DEFAULT_PAGE_SIZE);

        SearchRequest searchRequest = new SearchRequest(indices);
        searchRequest.source(searchSourceBuilder);

        // 执行请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();

        List<T> list = CollectionUtil.newArrayList();
        for (SearchHit hit : searchHits) {
            T t = JSONUtil.toBean(hit.getSourceAsString(), clazz);
            list.add(t);
        }
        return list;
    }

}
