package com.youlai.common.elasticsearch.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
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
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hxr
 * @date 2021-03-05
 */
@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final RestHighLevelClient client;


    @SneakyThrows
    public long count(QueryBuilder queryBuilder, String... indices) {
        // 构造请求
        CountRequest countRequest = new CountRequest(indices);
        countRequest.query(queryBuilder);

        // 执行请求
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        long count = countResponse.getCount();
        return count;
    }

    @SneakyThrows
    public long countDistinct(QueryBuilder queryBuilder, String field, String... indices) {
        String distinctKey = "distinctKey"; // 自定义计数去重key，保证上下文一致

        // 构造计数聚合 cardinality:集合中元素的个数
        CardinalityAggregationBuilder aggregationBuilder = AggregationBuilders
                .cardinality(distinctKey).field(field);

        // 构造搜索源
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder).aggregation(aggregationBuilder);

        // 构造请求
        SearchRequest searchRequest = new SearchRequest(indices);
        searchRequest.source(searchSourceBuilder);

        // 执行请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        ParsedCardinality result = searchResponse.getAggregations().get(distinctKey);
        return result.getValue();
    }

    /**
     * 日期统计
     *
     * @param queryBuilder 查询条件
     * @param field        聚合字段，如：登录日志的 date 字段
     * @param interval     统计时间间隔，如：1天、1周
     * @param indices      索引名称
     * @return
     */
    @SneakyThrows
    public Map<String, Long> dateHistogram(QueryBuilder queryBuilder, String field, DateHistogramInterval interval, String... indices) {

        String dateHistogramKey = "dateHistogramKey"; // 自定义日期聚合key，保证上下文一致

        // 构造聚合
        AggregationBuilder aggregationBuilder = AggregationBuilders
                .dateHistogram(dateHistogramKey) //自定义统计名，和下文获取需一致
                .field(field) // 日期字段名
                .format("yyyy-MM-dd") // 时间格式
                .calendarInterval(interval) // 日历间隔，例： 1s->1秒 1d->1天 1w->1周 1M->1月 1y->1年 ...
                .minDocCount(0); // 最小文档数，比该值小就忽略

        // 构造搜索源
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
                .query(queryBuilder)
                .aggregation(aggregationBuilder)
                .size(0);

        // 构造SearchRequest
        SearchRequest searchRequest = new SearchRequest(indices);
        searchRequest.source(searchSourceBuilder);

        searchRequest.indicesOptions(
                IndicesOptions.fromOptions(
                        true, // 是否忽略不可用索引
                        true, // 是否允许索引不存在
                        true, // 通配符表达式将扩展为打开的索引
                        false // 通配符表达式将扩展为关闭的索引
                ));

        // 执行请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理结果
        ParsedDateHistogram dateHistogram = searchResponse.getAggregations().get(dateHistogramKey);

        Iterator<? extends Histogram.Bucket> iterator = dateHistogram.getBuckets().iterator();

        Map<String, Long> map = new HashMap<>();
        while (iterator.hasNext()) {
            Histogram.Bucket bucket = iterator.next();
            map.put(bucket.getKeyAsString(), bucket.getDocCount());
        }
        return map;
    }

    @SneakyThrows
    public <T extends BaseDocument> List<T> search(QueryBuilder queryBuilder, Class<T> clazz, String... indices) {
        List<T> list = this.search(queryBuilder, null, 1, 10, clazz, indices);
        return list;
    }

    @SneakyThrows
    public <T extends BaseDocument> List<T> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, Integer page, Integer size, Class<T> clazz, String... indices) {
        // 构造SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.sort(sortBuilder);
        searchSourceBuilder.from((page - 1) * size);
        searchSourceBuilder.size(size);
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
            t.setId(hit.getId()); // 数据的唯一标识
            t.setIndex(hit.getIndex());// 索引
            list.add(t);
        }
        return list;
    }

    @SneakyThrows
    public boolean deleteById(String id, String index) {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return true;
    }


    @Data
    public static class BaseDocument {

        /**
         * 数据唯一标识
         */
        private String id;

        /**
         * 索引名称
         */
        private String index;
    }


    @Data
    public class LoginRecord extends BaseDocument {

        private String clientIP;

        private long elapsedTime;

        private Object message;

        private String token;

        private String username;

        private String loginTime;

        private String region;

        /**
         * 会话状态 0-离线 1-在线
         */
        private Integer status;

    }


}
