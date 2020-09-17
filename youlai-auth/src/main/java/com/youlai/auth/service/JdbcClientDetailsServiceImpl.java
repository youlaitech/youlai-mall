package com.youlai.auth.service;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class JdbcClientDetailsServiceImpl extends JdbcClientDetailsService {
    public JdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }
}
