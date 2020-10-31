package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.entity.OauthClientDetails;
import com.youlai.admin.mapper.OauthClientDetailsMapper;
import com.youlai.admin.service.IOauthClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {
}
