package com.youlai.mall.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.youlai.mall.auth.entity.SysUser;
import com.youlai.mall.auth.service.ISysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class TokenController {

    @Autowired
    private TokenStore jdbcTokenStore;

    @Autowired
    private ISysUserService iSysUserService;

    @GetMapping("/current")
    public R<SysUser> current(Principal principal) {
        String username = principal.getName();
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        return R.ok(user);
    }

    @DeleteMapping("/logout")
    public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StringUtils.isBlank(authHeader)) {
            return R.failed("");
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE.toLowerCase(), "").trim();
        OAuth2AccessToken oAuth2AccessToken = jdbcTokenStore.readAccessToken(tokenValue);
        if (oAuth2AccessToken == null || StringUtils.isBlank(oAuth2AccessToken.getValue())) {
            return R.failed("");
        }
        jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
        return R.ok(null);
    }
}
