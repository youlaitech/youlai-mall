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
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hxr
 * @date 2021-03-05
 */
@Service
@AllArgsConstructor

public class ElasticSearchService {

    private RestHighLevelClient client;


    @SneakyThrows
    public long count(QueryBuilder queryBuilder, String... indices) {
        // 构造搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        // 构造请求
        CountRequest countRequest = new CountRequest(indices);
        countRequest.source(searchSourceBuilder);
        // 执行请求
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        long count = countResponse.getCount();
        return count;
    }

    @SneakyThrows
    public Map<String, Long> dateHistogram(QueryBuilder queryBuilder,String field, DateHistogramInterval interval, String... indices) {

        // 构造AggregationBuilder
        AggregationBuilder aggregationBuilder=AggregationBuilders
                .dateHistogram("dateHistogram") //自定义统计名，和下文获取需一致
                .field(field) // 日期字段名
                .format("yyyy-MM-dd") // 时间格式
                .calendarInterval(interval) // 日历间隔，例： 1s->1秒 1d->1天 1w->1周 1M->1月 1y->1年 ...
                .minDocCount(0); // 最小文档数，比该值小就忽略

        // 构造SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder
                .query(queryBuilder)
                .aggregation(aggregationBuilder)
                .size(0);

        // 构造SearchRequest
        SearchRequest searchRequest=new SearchRequest(indices);
        searchRequest.source(searchSourceBuilder);

        // 执行请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理结果
        ParsedDateHistogram dateHistogram = searchResponse.getAggregations().get("dateHistogram");

        Iterator<? extends Histogram.Bucket> iterator = dateHistogram.getBuckets().iterator();

        while (iterator.hasNext()){
            Histogram.Bucket bucket = iterator.next();
            System.out.println(bucket.getKeyAsString()+":"+bucket.getDocCount());
        }

        return null;
    }

    @SneakyThrows
    public <T> List<T> search(QueryBuilder queryBuilder, Class<T> clazz, String... indices) {
        // 构造SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(EsConstants.DEFAULT_PAGE_SIZE);
        // 构造SearchRequest
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
