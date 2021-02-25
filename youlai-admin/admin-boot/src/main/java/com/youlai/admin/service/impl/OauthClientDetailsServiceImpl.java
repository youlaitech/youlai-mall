package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.pojo.entity.OauthClientDetails;
import com.youlai.admin.mapper.OauthClientDetailsMapper;
import com.youlai.admin.service.IOauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {
}
