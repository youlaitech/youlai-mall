package com.youlai.admin;

import com.youlai.common.elasticsearch.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ElasticSearchTests {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    public void search() {
        elasticSearchService.search(null,null,"logstash-login-2021.03.05");
    }
}
