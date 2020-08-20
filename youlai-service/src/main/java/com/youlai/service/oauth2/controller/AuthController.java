package com.youlai.service.oauth2.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.youlai.common.result.Result;
import com.youlai.service.system.entity.SysUser;
import com.youlai.service.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/oauth2")
public class AuthController {

    @Autowired
    private TokenStore jdbcTokenStore;

    @Autowired
    private ISysUserService iSysUserService;

    @GetMapping("/info")
    public Result<SysUser> user(Principal principal) {
        String username = principal.getName();
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
        return Result.success(user);
    }

    @DeleteMapping("/logout")
    public Result logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StringUtils.isBlank(authHeader)) {
            return Result.error();
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE.toLowerCase(), "").trim();
        OAuth2AccessToken oAuth2AccessToken = jdbcTokenStore.readAccessToken(tokenValue);
        if (oAuth2AccessToken == null || StringUtils.isBlank(oAuth2AccessToken.getValue())) {
            return Result.success();
        }
        jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
        return Result.success();
    }

}
