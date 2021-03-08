package com.youlai.admin.controller;

import com.youlai.common.elasticsearch.service.ElasticSearchService;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.RequestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author hxr
 * @description 首页控制台
 * @date 2021-03-08
 */
@Api(tags = "首页控制台")
@RestController
@RequestMapping("/api.admin/v1/dashboard")
@Slf4j
@AllArgsConstructor
public class DashboardController {

    ElasticSearchService elasticSearchService;

    @ApiOperation(value = "登录次数统计", httpMethod = "GET")
    @GetMapping("/login_counts")
    public Result visits() {
        int days = 7; //统计天数
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String startDate = now.plusDays(-days).format(formatter);
        String endDate = now.format(formatter);

        String[] indices = new String[days];
        for (int i = 0; i < days; i++) {
            indices[i] = "youlai-auth-login-" + now.plusDays(-i).format(formatter);
        }

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("date").from(startDate).to(endDate);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(rangeQueryBuilder)
                /*.must(QueryBuilders.wildcardQuery("accessToken", "*"))*/; // 登录成功统计

        Map<String, Long> map = elasticSearchService.dateHistogram(boolQueryBuilder, "date", DateHistogramInterval.days(1), indices);
        log.info(map.toString());
        return Result.success(map);
    }

    @ApiOperation(value = "访问次数统计（我的）", httpMethod = "GET")
    @GetMapping("/login_counts/me")
    public Result getMyVisits() {
        String username = RequestUtils.getUsername();
        return Result.success(username);
    }

}
