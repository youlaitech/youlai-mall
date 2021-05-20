package com.youlai.auth.service;

import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.security.Principal;
import java.util.Map;

/**
 * @author haoxr
 * @description TODO
 * @createTime 2021/5/20 23:35
 */
public interface IAuthService {

    OAuth2AccessToken login(Principal principal, Map<String, String> parameters) ;
}
