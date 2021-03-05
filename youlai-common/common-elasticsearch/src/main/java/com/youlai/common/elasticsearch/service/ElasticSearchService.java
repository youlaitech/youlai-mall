package com.youlai.common.elasticsearch.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.common.elasticsearch.constant.ElasticSearchConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
public class ElasticSearchService<T> {

    private RestHighLevelClient client;

    @SneakyThrows
    public SearchResponse search(SearchRequest searchRequest) {
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }

    @SneakyThrows
    public List search(QueryBuilder queryBuilder, Class<T> clazz, String... indexes) {
        SearchRequest searchRequest = new SearchRequest(indexes);

        // 构造请求参数
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(ElasticSearchConstants.DEFAULT_PAGE_SIZE);
        searchRequest.source(searchSourceBuilder);

        // 执行搜索请求
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
