package com.youlai.admin;

import com.youlai.common.elasticsearch.service.ElasticSearchService;
import com.youlai.common.web.pojo.domain.LoginLog;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
}
