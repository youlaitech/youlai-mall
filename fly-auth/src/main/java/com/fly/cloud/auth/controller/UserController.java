package com.fly.cloud.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fly.cloud.auth.service.ISysUserService;
import com.fly.common.constant.enums.CommonEnums;
import com.fly.common.pojo.dto.Result;
import com.fly.common.pojo.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private TokenStore jdbcTokenStore;

    @GetMapping("/info")
    public Result<SysUser> user(Principal principal) {
        String username = principal.getName();
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        var result= Result.success(user);
        return result;
    }

    @DeleteMapping("/logout")
    public Result logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader){
        if(StringUtils.isBlank(authHeader)){
            return new Result(CommonEnums.LOGOUT_ERROR);
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE.toLowerCase(), "").trim();
        OAuth2AccessToken oAuth2AccessToken = jdbcTokenStore.readAccessToken(tokenValue);
        if (oAuth2AccessToken == null || StringUtils.isBlank(oAuth2AccessToken.getValue())) {
            return new Result(CommonEnums.LOGOUT_ERROR);
        }
        jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
        return Result.success();
    }
}
