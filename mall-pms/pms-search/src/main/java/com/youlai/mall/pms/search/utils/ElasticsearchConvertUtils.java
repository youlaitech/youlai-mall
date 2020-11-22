package com.youlai.mall.pms.search.utils;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Elasticsearch 数据转换工具类
 */
@Component
public class ElasticsearchConvertUtils {

    /**
     * searchHits 转换 List<T> 数据
     *
     * @param searchHits searchHits查询结果集
     * @param <T>
     * @return
     */
    public static <T> List<T> searchHitsConvertDataList(SearchHits<T> searchHits) {
        if (searchHits != null && searchHits.hasSearchHits()) {
            List<T> response = new ArrayList<>();
            searchHits.forEach(searchHit ->
                    response.add(searchHit.getContent()));
            return response;
        }
        return null;
    }

    /**
     * searchHits 转换 List<? extend Terms.Bucket> 数据
     *
     * @param searchHits          searchHits查询结果集
     * @param termAggregationName term聚合名称
     * @param <T>
     * @return
     */
    public static <T> List<? extends Terms.Bucket> searchHitsConvertTermsBuckets(SearchHits<T> searchHits, String termAggregationName) {
        if (searchHits != null && searchHits.hasAggregations() && StringUtils.isNotEmpty(termAggregationName)) {
            Aggregations aggregations = searchHits.getAggregations();
            if (aggregations != null) {
                ParsedTerms parsedTerms = aggregations.get(termAggregationName);
                if (parsedTerms != null) {
                    return parsedTerms.getBuckets();
                }
            }
        }
        return null;
    }

    /**
     * {@link Terms.Bucket} 转换获取子聚合 {@link Range.Bucket}
     *
     * @param bucket              {@link Terms.Bucket} 桶
     * @param rangAggregationName range聚合名称
     * @return
     */
    public static List<? extends Range.Bucket> termsBucketConvertRangeBucket(Terms.Bucket bucket, String rangAggregationName) {
        if (bucket != null && StringUtils.isNotEmpty(rangAggregationName)) {
            Aggregations aggregations = bucket.getAggregations();
            if (aggregations != null) {
                ParsedRange parsedRange = aggregations.get(rangAggregationName);
                if (parsedRange != null) {
                    return parsedRange.getBuckets();
                }
            }
        }
        return null;
    }

}
