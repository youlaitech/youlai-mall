package com.youlai.lab.spring.aop.transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 开启事务支持
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/26 0026 21:17
 */
//@EnableTransactionManagement
public class MytransactionManager {
    // 使用DataSourceTransactionManager来管理事务
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }
}
