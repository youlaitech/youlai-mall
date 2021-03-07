package com.youlai.admin;

import com.youlai.common.elasticsearch.service.ElasticSearchService;
import com.youlai.common.web.pojo.domain.LoginLog;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class ElasticSearchTests {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void search() {
        List<LoginLog> list = elasticSearchService.search(null, LoginLog.class, "youlai-auth-login-2021-03-06");
        System.out.println(list.toString());
    }

    @Test
    public void count(){
        long count = elasticSearchService.count(null, "youlai-auth-login-2021-03-06");
        System.out.println(count);
    }


    @Test
    public void group(){
        Map<String, Long> groupMap = elasticSearchService.dateHistogram(null, "date",DateHistogramInterval.days(1),"youlai-auth-login-2021-03-07");
    }
}
